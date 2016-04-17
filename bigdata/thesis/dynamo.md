## background

### System Assumptions and Requirements 
**Query Model**  
simple read and write operations to a data item that is uniquely identified by
a key. State is stored as binary objects (i.e., blobs) identified by unique
keys. 

**ACID Properties**   
Experience at Amazon has shown that data stores that provide ACID
guarantees tend to have poor availability. This has been widely
acknowledged by both the industry and academia. Dynamo targets applications that
operate with weaker consistency(the “C” in ACID) if this results in high
availability. Dynamo does not provide any isolation guarantees and permits only
single key updates. 

**Efficiency**  
 must be capable of meeting such stringent SLAs . Services must be able to
configure Dynamo such that they consistently achieve their latency and
throughput requirements. 

### Service Level Agreements (SLA) 

 Clients and services engage in a Service Level Agreement (SLA), a formally
negotiated contract where a client and a service agree on several
system-related characteristics, which most prominently include
the client’s expected request rate distribution for a particular API
and the expected service latency under those conditions. 

A common approach in the industry for forming a performance oriented SLA is to
describe it using average, median and expected variance. At Amazon we have
found that these metrics are not
good enough if the goal is to build a system where all customers
have a good experience, rather than just the majority.  At Amazon, SLAs are
expressed and measured at the 99.9th percentile of the distribution. 

### Design Considerations

### Consistency
For systems prone to server and network failures, availability can
be increased by using **optimistic replication techniques**, where
changes are allowed to propagate to replicas in the background,
and concurrent, disconnected work is tolerated. The challenge
with this approach is that it can lead to conflicting changes which
must be detected and resolved. 

An important design consideration is to decide when to perform
the process of resolving update conflicts, i.e., whether conflicts
should be resolved during reads or writes.

Many traditional data stores execute conflict resolution during writes and keep
the read complexity simple. In such systems, writes may be rejected if the data
store cannot reach all (or a majority of) the replicas at a given time. On the
other hand, Dynamo targets the design space
of an “always writeable” data store (i.e., a data store that is highly
available for writes).


The next design choice is who performs the process of conflict
resolution. This can be done by the data store or the application. If
conflict resolution is done by the data store, its choices are rather
limited. In such cases, the data store can only use simple policies,
such as “last write wins” , to resolve conflicting updates. On
the other hand, since the application is aware of the data schema it
can decide on the conflict resolution method that is best suited for
its client’s experience. 

### Incremental scalability
Dynamo should be able to scale out one storage host (henceforth, referred to
as “node”) at a time, with minimal impact on both operators of the system and
the system itself. 

### Symmetry
 Every node in Dynamo should have the same set of responsibilities as its
peers; there should be no distinguished node or nodes that take special
roles or extra set of responsibilities.

### Decentralization
An extension of symmetry, the design should favor decentralized peer-to-peer
techniques over centralized control.

### Heterogeneity
The system needs to be able to exploit heterogeneity in the infrastructure it
runs on. 

## System Architecture
### Partitioning Algorithm 
Dynamo’s partitioning scheme relies on consistent hashing to distribute the load
across multiple storage hosts. The principle
advantage of consistent hashing is that departure or arrival of a
node only affects its immediate neighbors and other nodes remain
unaffected.

Dynamo uses a variant of consistent hashing : instead of mapping a node to
a single point in the circle, each node gets assigned to multiple points in the
ring. To this end, Dynamo uses the concept of “virtual nodes”. A virtual node
looks like a single node in the system, but each node can be responsible for
more than one virtual node. 

Using virtual nodes has the following advantages:

+   If a node becomes unavailable (due to failures or routine
maintenance), the load handled by this node is evenly dispersed across the
remaining available nodes.
+   When a node becomes available again, or a new node is added to the
system, the newly available node accepts a roughly equivalent amount of load
from each of the other available nodes.
+   The number of virtual nodes that a node is responsible can
decided based on its capacity, accounting for heterogeneity
in the physical infrastructure. 

### Replication
To achieve high availability and durability, Dynamo replicates its
data on multiple hosts.  Each key, k, is assigned to a coordinator node.The
coordinator is in charge of the replication of the data items
that fall within its range. In addition to locally storing each key
within its range, the coordinator replicates these keys at the N-1
clockwise successor nodes in the ring. 

The list of nodes that is responsible for storing a particular key is
called the preference list. To account for node failures, preference list
contains more than N nodes.

### Data Versioning  
Dynamo treats the result of each modification as a new and immutable
version of the data. It allows for multiple versions of an object to be
present in the system at the same time. Most of the time, new versions
subsume the previous version(s), and the system itself can determine the
authoritative version (syntactic reconciliation).
However, version branching may happen, in the presence of failures combined with
concurrent updates, resulting in conflicting versions of an object. In
these cases, the system cannot reconcile the multiple versions of the same
object and the client must perform the reconciliation in order to collapse
multiple branches of data evolution back into one (semantic reconciliation). 

Dynamo uses **vector clocks** in order to capture causality between
different versions of the same object. A vector clock is effectively a list of
(node, counter) pairs. One vector clock is associated with every version of
every object. One can determine whether two versions of an object are on
parallel branches or have a causal ordering, by examine their vector
clocks. If the counters on the first object’s clock are less-than-or-equal to
all of the nodes in the second clock, then the first is an ancestor of the
second and can be forgotten. Otherwise, the two changes are considered to be
in conflict and require reconciliation. 

### Execution of get () and put () operations 
To maintain consistency among its replicas, Dynamo uses a consistency
protocol similar to those used in quorum systems. This protocol has two key
configurable values: R and W. R is the minimum number of nodes that must
participate in a successful read operation. W is the minimum number of
nodes that must participate in a successful write operation. Setting R and
W such that R + W > N yields a quorum-like system. In this model, the
latency of a get (or put) operation is dictated by the slowest of the R (or W)
replicas. For this reason, R and W are usually configured to be less than N,
to provide better latency. 

Upon receiving a put() request for a key, the coordinator generates the
vector clock for the new version and writes the new version locally. The
coordinator then sends the new version (along with the new vector clock) to the
N highest-ranked reachable nodes. If at least W-1 nodes respond then the
write is considered successful. 

Similarly, for a get() request, the coordinator requests all existing
versions of data for that key from the N highest-ranked reachable nodes in the
preference list for that key, and then waits for R responses before
returning the result to the client. If the coordinator ends up gathering
multiple versions of the data, it returns all the versions it deems to be
causally unrelated. The divergent versions are then reconciled and the
reconciled version superseding the current versions is written back. 

### Handling Failures: Hinted Handoff 
Using hinted handoff, Dynamo ensures that the read and write
operations are not failed due to temporary node or network
failures. Applications that need the highest level of availability
can set W to 1, which ensures that a write is accepted as long as a
single node in the system has durably written the key it to its local
store. Thus, the write request is only rejected if all nodes in the
system are unavailable.

### Handling permanent failures: Replica synchronization

To detect the inconsistencies between replicas faster and to
minimize the amount of transferred data, Dynamo uses Merkle
trees. A Merkle tree is a hash tree where leaves are hashes of
the values of individual keys. Parent nodes higher in the tree are
hashes of their respective children. The principal advantage of
Merkle tree is that each branch of the tree can be checked
independently without requiring nodes to download the entire tree or the entire data set. 
Moreover, Merkle trees help in reducing the amount of data that needs to be transferred while checking for inconsistencies among replicas.  
Dynamo uses Merkle trees for anti-entropy as follows: Each node
maintains a separate Merkle tree for each key range (the set of
keys covered by a virtual node) it hosts. This allows nodes to
compare whether the keys within a key range are up-to-date.
> Written with [StackEdit](https://stackedit.io/).
