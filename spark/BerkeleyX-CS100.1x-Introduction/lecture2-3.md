## what is data science

**Data Science** aims to derive knowledge from big data, efficiently and intelligently.

[link](http://drewconway.com/zia/2013/3/26/the-data-science-venn-diagram)

### Data Science Topics

+   Data Acquisition  
+   Data Preparation  
    Extract, Transform, Load (ETL). 
    We need to **extract** data from the source(s)  
    We need to **load** data into the sink  
    We need to **transform** data at the source, sink, or in a staging area. 
+   Analysis
+   Data Presentation
+   Data Products
+   Observation and Experimentation


### from web company aspect:

+   Data Sources  
    Application databases 
    Logs from the services tier 
    Web crawl data 

+   ETL  
    Apache Flume, Apache Sqoop, Apache Pig, Apache Oozie,
Apache Crunch

+   Data Warehouse  
    Apache Hadoop/Apache Hive, Apache Spark/Spark SQL

+   Business Intelligence and Analytics  
    Custom dashboards: Oracle Argus, Razorflow  
    R

## The Big Data Problem

A single machine can no longer process or even store all
the data, Only solution is to distribute data over large clusters.

### hardware for big data

Use consumer-grade hardware, many desktop-like servers and add complexity in software.

problems with cheap hardware:

+   Failures
+   Network speeds, network slower than storage
+   Uneven performance

### What’s Hard About Cluster Computing

+   How to divide work across machines  
    Must consider network, data locality   
    Moving data may be very expensive

+   How to deal with failures?  
    1 server fails every 3 years ! with 10,000 nodes see 10 faults/day  
    Even worse: stragglers (not failed, but slow nodes)


### Apache Spark Motivation

Using Map Reduce for complex jobs, interactive queries
and online processing involves lots of disk I/O!

Keep more data in-memory.

spark and map reduce difference

aspect      | Hadoop Map Reduce | Spark
:-----------|:------------:|:------------:
 storage    | disk         | memory and disk     
 operations | map, reduce   | map, reduce, join, sample, etc..    
 execution model | batch   | batch,interactive, streaming      
 programming environment |  Java |    Scala, Java, R, and Python
