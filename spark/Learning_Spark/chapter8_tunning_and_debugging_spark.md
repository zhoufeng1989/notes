## Components of Execution: Jobs, Tasks and Stages

### User code defines a DAG (directed acyclic graph) of RDDs   
Operations on RDDs create new RDDs that refer back to their parents, thereby creating a graph.

### Actions force translation of the DAG to an execution plan  
When you call an **action** on an RDD it must be computed. This requires computing
its parent RDDs as well. Spark’s scheduler submits a **job** to compute all needed
RDDs. That job will have one or more **stages**, which are parallel waves of
computation composed of **tasks**.    
Each **stage** will correspond to one or more RDDs in the DAG. A single stage can correspond to multiple RDDs due to pipelining.
### Tasks are scheduled and executed on a cluster  
**Stages** are processed in order, with individual tasks launching to compute segments of the RDD. 
Once the final stage is finished in a job, the action is complete.
