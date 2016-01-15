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



> Written with [StackEdit](https://stackedit.io/).
