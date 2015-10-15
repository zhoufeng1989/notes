## Spark concepts


### Spark Context

Tells Spark how and where to access a cluster. 
The **master** parameter for a ```SparkContext``` determines which type and size of cluster to use.

 Parameter  | Description
:-----------|------------:
 local      | run Spark locally with one worker thread (no parallelism)   
 local[k]   | run Spark locally with K worker threads (ideally set to number of cores)
 spark://HOST:PORT |connect to a Spark standalone cluster
 mesos://HOST:PORT |connect to a Mesos cluster
 
 
### RDD

+   Immutable once constructed
+   Track lineage information to efficiently recompute lost data
+   Enable operations on collection of elements in paralle

You construct RDDs

+   by parallelizing existing Python collections (lists)
+   by transforming an existing RDDs
+   from files in HDFS or any other storage system 
+   

Two types of operations

+   transformation, lazy evaluation, Think of this as a recipe for creating result
+   action, transformed RDD is executed when action runs on it

you can cache your rdd

### Shared Variables

+   Boardcast  Variables (TODO: get more)
    +   Efficiently send large, read-only value to all workers   
        Ship to each worker only once instead of with each task
    +   Saved at workers for use in one or more Spark operations
    +   Like sending a large, read-only lookup table to all the nodes

+   Accumulators

    +   Aggregate values from workers back to driver
    +   Only driver can access value of accumulator
    +   For tasks, accumulators are write-only
    +   Use to count errors seen in RDD across workers
