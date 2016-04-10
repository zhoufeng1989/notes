
Bigtable is a **distributed** storage system for managing **structured** data.

Design goals are **wide applicability**, **scalability**, **high performance**,
and **high availability**.

## Data model

A Bigtable is a sparse, distributed, persistent multidimensional
sorted map. The map is indexed by a **row key**, **column key**, and
a **timestamp**. each value in the map
is an uninterpreted array of bytes.

```
(row:string, column:string, time:int64) → string
```

### rows

Every read or write of data under a single row key is **atomic** (regardless of
the number of different columns being read or written in the row), a design
decision that makes it easier for clients to reason about the system’s behavior
in the presence of concurrent
updates to the same row.

Bigtable maintains data in **lexicographic order** by row
key. The row range for a table is dynamically partitioned.
Each row range is called a **tablet**, which is the unit of distribution
and load balancing. As a result, reads of short
row ranges are efficient and typically require communication
with only a small number of machines. Clients
can exploit this property by selecting their row keys so
that they get **good locality** for their data accesses.

### column families

Column keys are grouped into sets called column families,
which form the **basic unit of access control**. All data
stored in a column family is usually of the same type (we
compress data in the same column family together).

A column family must be created before data can be stored
under any column key in that family; after a family has
been created, any column key within the family can be
used.

Usually have small numbers of column family, but the number of columns can vary.

A column key is named using the following syntax: ```family:qualifier```.

### timestamp

Each cell in a Bigtable can contain multiple versions of
the same data; these versions are indexed by timestamp.
Bigtable timestamps are 64-bit integers.

Different versions of a cell are stored in **decreasing timestamp order**,
so that the most recent versions can be read first.

The client can specify either that only the last n versions
of a cell be kept, or that only new-enough versions be
kept (e.g., only keep values that were written in the last
seven days).

## dependencies

### GFS

Bigtable uses the distributed Google File System (GFS) to store log and data
files.   

The Google SSTable file format is used internally to store Bigtable data. An
SSTable provides a persistent, ordered immutable map from keys to values, where
both keys and values are arbitrary byte strings.

Each SSTable contains a sequence of blocks (typically each block is 64KB in
size, but this is configurable). A block index (stored at the end of the
SSTable) is used to locate blocks; the index is loaded
into memory when the SSTable is opened. A lookup can be performed with a single
disk seek: we first find the appropriate block by performing a binary search in
the in-memory index, and then reading the appropriate block from disk.

### chubby

Bigtable relies on a highly-available and persistent
distributed lock service called Chubby.

Bigtable uses Chubby for a variety of tasks:    
to ensure that there is at most one active master at any time; to
store the bootstrap location of Bigtable data ; to discover tablet servers and
finalize tablet server deaths; to store Bigtable schema
information (the column family information for each table);
and to store access control lists. If Chubby becomes
unavailable for an extended period of time, Bigtable becomes
unavailable. 

## implementation 

The Bigtable implementation has three major components:
a **library** that is linked into every client, one **master server**, and
**many tablet servers**.

The **master** is responsible for assigning tablets to tablet servers, detecting
the addition and expiration of tablet servers, balancing tablet-server load, and
garbage collection of files in GFS. In addition, it handles schema changes such
as table and column family creations. Each tablet server manages a set of
tablets (typically we have somewhere between ten to a thousand tablets per
tablet server). 

The **tablet server** handles read and write requests to the tablets that it has
loaded, and also splits tablets that have grown too large.

As with many single-master distributed storage systems, client data **does not
move through the master**: clients communicate directly with tablet servers for
reads and writes. Because Bigtable clients do not rely on the master for tablet
location information, most clients
never communicate with the master. As a result, the master
is lightly loaded in practice.

A Bigtable cluster stores a number of tables. Each table consists of a set of
tablets, and each tablet contains all data associated with a row range.
Initially, each table consists of just one tablet. As a table grows, it is
automatically split into multiple tablets, each approximately 100-200 MB in size
by default.

### tablet location.

We use a three-level hierarchy analogous to that of a B+ tree to store tablet
location information
> Written with [StackEdit](https://stackedit.io/).
