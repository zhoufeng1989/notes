## Transformations  

### transformation about key  
+ reduceByKey(func)     
```reduceByKey``` is different from ```reduce```, return another rdd.     
datasets can have very large numbers of keys, ```reduceByKey()``` is not implemented as an action that returns a value to the 
user program. Instead, it returns a new RDD consisting of each key and the reduced value for that key.   
+ foldByKey   
+ combineByKey  
There are many options for combining our data by key. 
Most of them are implemented on top of ```combineByKey()``` but provide a simpler interface. 
In any case, using one of the specialized aggregation functions in Spark can be much faster than the naive approach 
of grouping our data and then reducing it.
+ keys   
return and rdd of just the keys, may be dumplicate values.  
+ sortByKey  

### transformation about value
+ mapValues(func)    
apply function to each value without changing the key.   
+ flatMapValues(func)    
+ values   
return and rdd of just the values   

### Tunning the level of parallelism, repartition and coalesce      
Every RDD has a fixed number of partitions that determine the degree of parallelism to use when executing operations on the RDD.  
When performing aggregations or grouping operations, we can ask Spark to use a specific number of partitions. 
Spark will always try to infer a sensible default value based on the size of your cluster, but in some cases you will want to tune 
the level of parallelism for better performance.    
Most of the grouping and aggregation operations accept an extra parameter for repartitions.  
Sometimes, we want to change the partitioning of an RDD outside the context of grouping and aggregation operations. 
For those cases, Spark provides the ```repartition()``` function, which shuffles the data across the network to create a new set of partitions. 
Keep in mind that repartitioning your data is a fairly expensive operation. 
Spark also has an optimized version of repartition() called ```coalesce()``` that allows avoiding data movement, but only if you are decreasing the number of RDD partitions. 

### Transformations on two Rdds  
+ join、leftOuterJoin、rightOuterJoin   
+ cogroup  
+ union、subtractByKey  
