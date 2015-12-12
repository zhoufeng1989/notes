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
