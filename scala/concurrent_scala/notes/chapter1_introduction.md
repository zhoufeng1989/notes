## Concurrent programming 

In concurrrent programming, we express a program as a set of concurrent
computations that **executes during overlapping time intervals** and **coordinate in
some way**.

### Brief overview  
**concurrent programming advantages**  
+   imporve program performace.(multiprocessors)
+   faster I/O operations.  
    Instead of blocking or periodically polling I/O to check data, can react to I/O request
    immediately. This is one of the reasons why concurrent programming support
    existed in programming languages even before the appearance of multiprocessors.   
+   simplify the implementation and maintainability of programs.   
    It can be more conventient to divide the program into smaller, independent
    computaions than to incorporate everything into one large program.

**synchronization**   
coordination of multiple executions in a concurrent system is called synchronization.
Synchronization includes mechanisms used to order concurrent executions
communicate, that is, how they exchange information.(shared memory communication, message-passing communication)

**process and thread**  
At the lowest level, concurrent executions are represented by entities called by
processes and threads. Process and threads traditionally use entities such as
locks and monitors to order parts of their execution. Establishing an order
between the threads ensures that the memory modifications done by one thread are
visiable to a thread that executes later.

### Modern concurrency paradigms    
A high-level concurrency framework express **which goal** tho achieve, rather
than **how tho achieve** that goal.
