#HDFS

## Desgin 
HDFS is a filesystem designed for storing **very large files** with **streaming data access patterns**, running on clusters of **commodity hardware**.

### Streaming data access pattern
HDFS is built around the idea that the most efficient data processing pattern is a **write-once, read-many-times pattern**. A dataset is typically generated or copied from source, and then various analyses are performed on that dataset over time. Each analysis will involve a large proportion, if not all, of the dataset, so the time to read the whole dataset is more important than the latency in reading the first record.

### Areas where HDFS is not a good fit:

+	Low-latency data access  
	HDFS is optimized for delivering a high throughput of data, and this may be at the expense of latency.
	
+	Lots of small files
	Because the namenode holds filesystem metadata in memory, the limit to the number of files in a filesystem is governed by the amount of memory on the namenode.
	
+	Multiple writers, arbitrary file modifications
	Files in HDFS may be written to by a single writer. **Writes are always made at the end of the file, in append-only fashion.** There is no support for multiple writers or for modifications at arbitrary offsets in the file.
	
## HDFS concepts

### Blocks
HDFS's unit is blocks,like in a filesystem for a single disk, files in HDFS are broken into block-sized chunks, which are stored as independent units. Unlike a filesystem for a single disk, a file in HDFS that is smaller than a single block does not occupy a full block’s worth of underlying storage.

#### Why HDFS get large block than file system.

HDFS blocks are large compared to disk blocks, and the reason is to minimize the cost of seeks. If the block is large enough, the time it takes to transfer the data from the disk can be significantly longer than the time to seek to the start of the block. Thus, transferring a large file made of multiple blocks operates at the disk transfer rate.

#### Advantages to have the block abstraction.

+	a file can be larger than any single disk in the network. There’s nothing that requires the blocks from a file to be stored on the same disk, so they can take advantage of any of the disks in the cluster.

+	The storage subsystem deals with blocks, simplifying storage management (because blocks are a fixed size, it is easy to calculate how many can be stored on a given disk) and eliminating metadata concerns (because blocks are just chunks of data to be stored, file metadata such as permissions information does not need to be stored with the blocks, so another system can handle metadata separately).

+	blocks fit well with replication for providing fault tolerance and availability. To insure against corrupted blocks and disk and machine failure, each block is replicated to a small number of physically separate machines (typically three). If a block becomes unavailable, a copy can be read from another location in a way that is transparent to the client. A block that is no longer available due to corruption or machine failure can be replicated from its alternative locations to other live machines to bring the replication factor back to the normal level.

### Namenodes and Datanodes

The **namenode** manages the filesystem namespace. It maintains the filesystem tree and the metadata for all the files and directories in the tree. This information is stored persistently on the local disk in the form of two files: the namespace image and the edit log. The namenode also knows the datanodes on which all the blocks for a given file are located; however, it does not store block locations persistently, because this information is reconstructed from datanodes when the system starts.

**Datanodes** are the workhorses of the filesystem. They store and retrieve blocks when they are told to (by clients or the namenode), and they report back to the namenode periodically with lists of blocks that they are storing.

#### Ways to make the namenode resilient to failure
It is important to make the namenode resilient to failure, and Hadoop provides two mechanisms for this.

+	back up the files that make up the persistent state of the filesystem metadata. Hadoop can be configured so that the namenode writes its persistent state to multiple filesystems. These writes are synchronous and atomic. The usual configuration choice is to write to local disk as well as a remote NFS mount.

+	It is also possible to run a secondary namenode, which despite its name does not act as a namenode. Its main role is to **periodically merge the namespace image with the edit log** to prevent the edit log from becoming too large.

	The secondary namenode usually runs on a separate physical machine because it requires plenty of CPU and as much memory as the namenode to perform the merge. It keeps **a copy of the merged namespace image, which can be used in the event of the namenode failing**.
	
	However, the state of the secondary namenode lags that of the primary, so in the event of total failure of the primary, data loss is almost certain.
	
### Block caching

By default, a block is cached in only one datanode’s memory, although the number is configurable on a per-file basis. Job schedulers (for MapReduce, Spark, and other frameworks) can take advantage of cached blocks by running tasks on the datanode where a block is cached, for increased read performance. 

### HDFS federation
HDFS federation, introduced in the 2.x release series, allows a cluster to scale by adding namenodes, each of which manages a portion of the filesystem namespace. For example, one namenode might manage all the files rooted under /user, say, and a second namenode might handle files under /share.

Under federation, each namenode manages a namespace volume, which is made up of the metadata for the namespace, and a block pool containing all the blocks for the files in the namespace.