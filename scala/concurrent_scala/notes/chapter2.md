## Processes and Threads 
Most operating systems today rely on **preemptive multitasking**, in which each
program is repetitively assigned slices of execution time at a specific
processor. These slices are called **time slices**.Thus multitasking happens
**transparently** for the application programmer as well as the user.

Every thread describes the current state of the **program stack** and the
**program counter** during program execution. The program stack contains
a sequence of method invocations that are currently being executed, along with
the local variables and method parameters of each method. The program counter
describes the position of the current instruction in the current method.
A processor can advance the computation in some thread by **manipulating the
state of its stack or the state of the program objects and executing the
instruction at the current program counter**. 

**OS threads** are a programming facility provided by the OS, usually exposed
through an OS-specific programming interface. Unlike separate processes,
separate OS threads within the same process **share a region of memory**, and
communicate by writing to and reading parts of that memory. Another way to
define a process is to define it as a set of OS threads along with the memory
and resources shared by these threads.

（操作系统能够调度的最小单位是thread，process为多个thread提供了共享资源）

### threads in JVM
Starting a new JVM instance always creates only one process. Within the JVM
process, multiple threads can run simultaneously.  
Each Java thread is directly mapped to an OS thread. This means that Java
threads behave in a very similar way to the OS threads, and the JVM depends on
the OS and its restrictions.

### deterministic  
Given a particular input, it will always produce the same output, regardless of
the execution schedule chosen by the OS.
Most multithreaded programs are nondeterministic, and this is what makes
multithreaded programming so hard. 

### Basic Java thread operation.

see codes in src/main/scala/BasicThreads

## Atomic execution  

A **race condition** is a phenomenon in which the output of a concurrent program
depends on the execution schedule of the statements in the program.       
A race condition is not necessarily an incorrect program behavior. However, if
some execution schedule causes an undesired program output, the race condition
is considered to be a program error.    

**Atomic execution** of a block of code means that the individual statements in
that block of code executed by one thread cannot **interleave** with those
statements executed by another thread.   

The fundamental Scala construct that allows this sort of atomic execution is
called the **synchronized statement**, and it can be called on any object.

The synchronized call ensures that the subsequent block of code can only execute
if there is no other thread simultaneously executing this synchronized block of
code, or any other synchronized block of code called on the same this object.

Every object created inside the JVM has a special entity called an **intrinsic
lock** or a **monitor**, which is used to ensure that only one thread is
executing some synchronized block on that object. When a thread starts executing
the synchronized block, we can say that the T thread **gains ownership of the
x monitor**, or alternatively, **acquires** it. When a thread completes the
synchronized block, we can say that it **releases** the monitor.

## Reordering  
By the JMM specification, the JVM is allowed to **reorder** certain program
statements executed by one thread as long as it does not change the **serial
semantics of the program for that particular thread**. This is because some
processors do not always execute instructions in the program order.
Additionally, the threads do not need to write all their updates to the main
memory immediately, but can temporarily keep them cached in registers inside the
processor. This maximizes the **efficiency** of the program and allows better
compiler optimizations.

As a result, we always need to apply proper synchronization to ensure that the
writes by one thread are visible to another thread.
Writes by any thread executing the synchronized statement on an x object are
**not only atomic, but also visible to threads that execute synchronized on x**.

## Monitors and synchronization   

A synchronization mechanism that enforces access limits on a shared resource is
called a **lock**. Locks are also used to ensure that no two threads execute the
same code simultaneously; that is, they implement **mutual exclusion**.     
Each object on the JVM has a special built-in **monitor lock**, also called the
**intrinsic lock**.     

A natural consequence is that synchronized statements can be **nested**.
However, this cause **dead lock**. 

### Deadlocks  
A **deadlock** is a general situation in which two or more executions wait for
each other to complete an action before proceeding with their own action.   
The reason for waiting is that each of the executions obtains an exclusive
access to a resource that the other execution needs to proceed.  

Establish a **total order between resources** when acquiring them; this ensures
that no set of threads cyclically wait on the resources they previously
acquired.

### Guarded blocks  
A synchronized statement in which some condition is repetitively checked before
calling wait is called a **guarded block**. We can now use our insight on
guarded blocks to avoid the **busy-wait** in our worker thread in advance.       

An important property of the wait method is that it can cause **spurious
wakeups**. Occasionally, the JVM is allowed to wake up a thread that called wait
even though there is no corresponding notify call. To guard against this, we
must always use wait in conjunction with **a while loop** that checks the
condition, as in the previous example.

see code in src/main/scala/BasicThreads/SynchronizedPool.

### Interrupting threads and the graceful shutdown  
Calling the interrupt method on a thread that is in the waiting or timed waiting
state causes it to throw an InterruptedException. This exception can be caught
and handled, but in our case it will terminate the Worker thread. However, if we
call this method while the thread is running, the exception is not thrown and
the thread's interrupt flag is set. A thread that does not block must
periodically query the interrupt flag with the isInterrupted method.”

In the **graceful shutdown**, one thread sets the condition for the termination
and then calls notify to wake up a worker thread. The worker thread then
releases all its resources and terminates willingly. 

### Volatile variables  
+   “writes to and reads from volatile variables cannot be reordered in a single
thread.     
+   Second, writing to a volatile variable is immediately visible to all the
other threads.

Reads and writes to variables marked as volatile are never reordered. If a write
W to a volatile v variable is observed on another thread through a read R of the
same variable, then all the writes that preceded the write W are guaranteed to
be observed after the read R。
