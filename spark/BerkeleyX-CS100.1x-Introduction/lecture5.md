### Key Data Management Concepts

A **data model** is a collection of concepts for describing
data.

A **schema** is a description of a particular collection of
data, using a given data model.


### The Structure Spectrum

+   Structured(schema-first)   
    Relational Database, Formatted Messages

+   Semi-Structured(schema-later)    
    Documents XML, Tagged Text/Media

+   Unstructured(schema-never)
    Plain text, media


### file and filesystem

A **file** is a named sequence of bytes;     

A **filesystem** is a collection of files organized within an hierarchical namespace  
+   Responsible for laying out those bytes on physical media  
+   Stores file metadata   
+   Provides an API for interaction with files


## File Performance

+   Uncompressed read and write times are comparable
+   Binary I/O is much faster than text I/O
+   Compressed reads much faster than compressed writes   

        +   LZ4 is better than gzip
        +   LZ4 compression times approach raw I/O times
