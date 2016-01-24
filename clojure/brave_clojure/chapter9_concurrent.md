### Thread

####What is a theread

A thread is a subprogram. A program can have many threads, and each thread
executes its own set of instructions while enjoying shared access to the
program’s state.

You can think of a thread as an actual, physical piece of thread that strings
together a sequence of instructions. In my mind, the instructions are
marshmallows, because marshmallows are delicious.  So executing a program looks
like a bunch of marshmallows strung out on a line with an alligator traveling
down the line and eating them one by one.

### The Three Goblins: Reference Cells, Mutual Exclusion, and Dwarven Berserkers

+   reference cells  

The reference cell problem occurs when two threads can read and write to the
same location, and the value at the location depends on the order of the reads
and writes.

+   mutual exclusion

More than one thread write to a file at the same file, the content will be
garbage.

+   dwarven berserkers

deadlock

### Futures, Delays and Promises 

When you write serial code, you bind together these three events:

+   Task definition
+   Task execution
+   Requiring the task’s result

Futures, delays, and promises allow you to separate task definition, task
execution, and requiring the result.

#### Futures 

In Clojure, you can use futures to define a task and place it on another thread
without requiring the result immediately.






> Written with [StackEdit](https://stackedit.io/).
