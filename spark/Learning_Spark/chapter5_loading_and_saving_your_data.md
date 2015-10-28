## Data Sources 
### File Formats and File Systems   
support local or distributed filesystems, such as HDFS, S3, NFS,  
a variety of file formats including text, JSON, SequenceFiles, and protocol buffers.   
![File formats]
(chapter5_file_formats.png)

+  textFile  
load one single file or all files from one directory into a rdd 

+   wholeTextFiles  
load files from one directory into a pair rdd

+   saveAsTextFile  
takes a path and will output the contents of the RDD to that file. The path is
treated as a directory and Spark will output multiple files underneath that
directory. This allows Spark to write the output from multiple nodes. With this
method we don’t get to control which files end up with which segments of our
data, but there are other output formats that do allow this.

### Structured Data with Spark SQL  
+   Apache Hive with SQL   
+   JSON with SQL  

### Databases  
+   db with JDBC  
+   Cassandra  
+   Hbase  
+   Elasticsearch   

