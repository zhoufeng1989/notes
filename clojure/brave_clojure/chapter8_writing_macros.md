### Macros  

Macro definitions look much like function definitions. They have a name, an
optional document string, an argument list, and a body.    

The body will almost always return a list. This makes sense because macros are
a way of transforming a data structure into a form Clojure can evaluate, and
Clojure uses lists to represent function calls, special form calls, and macro
calls. 

You can use any function, macro, or special form within the macro body, and you
call macros just like you would a function or special form.

One key difference between functions and macros is that function arguments are
fully evaluated before they’re passed to the function, whereas macros receive
arguments as unevaluated data. 

#### Syntax quoting 

Syntax quoting returns unevaluated data structures, similar to normal quoting.
However, there are two important differences. 

One difference is that syntax quoting will return the fully qualified symbols
(that is, with the symbol’s namespace included). 

The other difference between quoting and syntax quoting is that the latter
allows you to unquote forms using the tilde, ~. 

##### Unquote splicing(~@)

Unquote splicing unwraps a seqable data structure, placing its contents directly
within the enclosing syntax-quoted data structure.

    ```
    `(+ ~(list 1 2 3))            ;(clojure.core/+ (1 2 3))
    `(+ ~@(list 1 2 3))           ;(clojure.core/+ 1 2 3)
    ```

### Things to Watch out for

#### variable capture

Variable capture occurs when a macro introduces a binding that, unknown to the
macro’s user, eclipses an existing binding.

If you want to introduce let bindings in your macro, you can use a gensym. The
**gensym** function produces unique symbols on each successive call:

    ```
        (gensym)
        (gensym 'prefix)
    ```

**Auto-gensyms** are more concise and convenient ways to use gensyms.Clojure
automatically ensures that each instance of x# resolves to the same symbol
within the same syntax-quoted list.

    ```
    `(let [name# "Larry Potter"] name#)
    ```

#### Double Evaluation

    ```
    (defmacro report
      [to-try]
      `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was failed:" ~to-try)))

    (report (do (Thread/sleep 1000) (+ 1 1)))


    (defmacro report2
      [to-try]
      `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was failed:" result#))))

    (report2 (do (Thread/sleep 1000) (+ 1 1)))

    (defmacro report3
      [to-try]
      (let [result (eval to-try)]
    (println result)
    `(if ~result
       (println (quote ~to-try) "was successful:" ~result)
       (println (quote ~to-try) "was failed:" ~result))))

    (report3 (do (Thread/sleep 1000) (+ 1 1)))
    ```

When you’re writing macros, it’s important to keep in mind the distinction
between symbols and values: macros are expanded before code is evaluated and
therefore don’t have access to the results of evaluation.

> Written with [StackEdit](https://stackedit.io/).
