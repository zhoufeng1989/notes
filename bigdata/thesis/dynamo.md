ckground

### System Assumptions and Requirements 
**Query Model**  
simple read and write operations to a data item that is uniquely identified by
a key. State is stored as binary objects (i.e., blobs) identified by unique
keys. 

**ACID Properties**   
Experience at Amazon has shown that data stores that provide ACID guarantees
tend to have poor availability. This has been widely acknowledged by both the
industry and academia. Dynamo targets applications that operate with weaker
consistency(the “C” in ACID) if this results in high availability. Dynamo does
not provide any isolation guarantees and permits only single key updates. 

**Efficiency**  
 must be capable of meeting such stringent SLAs . Services must be able to
configure Dynamo such that they consistently achieve their latency and
throughput requirements. 

### Service Level Agreements (SLA) 

 Clients and services engage in a Service Level Agreement (SLA), a formally
negotiated contract where a client and a service agree on several system-related
characteristics, which most prominently include
the client’s expected request rate distribution for a particular API
and the expected service latency under those conditions. 

A common approach in the industry for forming a performance oriented SLA is to
describe it using average, median and expected variance. At Amazon we have found
that these metrics are not
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
Dynamo should be able to scale out one storage host (henceforth, referred to as
“node”) at a time, with minimal impact on both operators of the system and the
system itself. 

### Symmetry
 Every node in Dynamo should have the same set of responsibilities as its peers;
there should be no distinguished node or nodes that take special roles or extra
set of responsibilities.

### Decentralization
An extension of symmetry, the design should favor decentralized peer-to-peer
techniques over centralized control.

### Heterogeneity
The system needs to be able to exploit heterogeneity in the infrastructure it
runs on. 

> Written with [StackEdit](https://stackedit.io/).
