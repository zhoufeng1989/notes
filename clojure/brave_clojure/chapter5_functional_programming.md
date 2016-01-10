### Pure functions

a pure function is     

+   It always returns the same result if given the same arguments. This is
called referential transparency     
+   It can’t cause any side effects. That is, the function can’t make any
changes that are observable outside the function itself—for example, by changing
an externally accessible mutable object or writing to a file.

These qualities make it easier for you to reason about your programs because the
functions are completely isolated, unable to impact other parts of your system.

#### Living with Immutable Data Structures

Immutable data structures ensure that your code won’t have side effects.

##### Recursion Instead of for/while
##### Function Composition Instead of Attribute Mutation   

In OOP, one of the main purposes of classes is to protect against unwanted
modification of private data—something that isn’t necessary with immutable data
structures. You also have to tightly couple methods with classes, thus limiting
the reusability of the methods.

By both a) decoupling functions and data, and b) programming to a small set of
abstractions, you end up with more reusable, composable code. You gain power and
lose nothing.

Going beyond immediately practical concerns, the differences between the way you
write object-oriented and functional code point to a deeper difference between
the two mindsets. Programming is about manipulating data for your own nefarious
purposes (as much as you can say it’s about anything). In OOP, you think about
data as something you can embody in an object, and you poke and prod it until it
looks right. During this process, your original data is lost forever unless
you’re very careful about preserving it. By contrast, in functional programming
you think of data as unchanging, and you derive new data from existing
data.During this process, the original data remains safe and sound. 

### Cool Things to Do with Pure Functions   

You can derive new functions from existing functions in the same way that you
derive new data from existing data. 

+   comp  

        ```
        ((comp inc *) 2 3 4)
        
        (defn my-comp
          ([f] (fn [& args] (apply f args)))
          ([f & others] (fn [& args]
                          (f (apply (apply my-comp others) args)))))
        ```
        
+   memoize   

**Memoization** lets you take advantage of referential transparency by storing
the arguments passed to a function and the return value of the function. That
way, subsequent calls to the function with the same arguments can return the
result immediately.

    ```
    (defn sleepy-identity
      [x]
      (Thread/sleep 5000)
      x)

    (def memo-sleepy-identity (memoize sleepy-identity))
    ```
    
> Written with [StackEdit](https://stackedit.io/).
