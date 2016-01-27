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

The **future** function returns a reference value that you can use to request
the result. You can use the reference value to request a future’s result(use
**deref** or **@**), but if the future isn’t done computing the result, you’ll
have to wait.

A future’s result value is the value of the last expression evaluated in its
body. A future’s body executes only once, and its value gets cached. 

    ```
    (let [result (future (println "In the future")(+ 1 1))]
    (println "deref: " (deref result))
    (println "@: " @result))
    ```

Sometimes you want to place a time limit on how long to wait for a future. To do
that, you can pass deref a number of milliseconds to wait along with the value
to return if the deref times out.
    ```
    (deref (future (Thread/sleep 5000) 0) 1000 10)
    ```
you can interrogate a future using **realized?** to see if it’s done running.
    ```
    (let [f (future (Thread/sleep 2000))]
        (println (realized? f))
        (deref f)
         (println (realized? f))
  )
  ```

#### delays

**Delays** allow you to define a task without having to execute it or require
the result immediately.You can evaluate the delay and get its result by
dereferencing it or by using **force**.

**force** also wait the task to complete, and caches the result.

    ```
     (def delay-test (delay (println "This is printed only once!") 10))
     (deref delay-test)
     @delay-test
     ``` 

One way you can use a delay is to fire off a statement the first time one future
out of a group of related futures finishes. 

#### promise

**Promises** allow you to express that you expect a result without having to
define the task that should produce it or when that task should run. 

use as callback.
    ```
    (let [p (promise)]
        (future (println @p))
        (Thread/sleep 3000)
        (deliver p 1000))
    ```
> Written with [StackEdit](https://stackedit.io/).
