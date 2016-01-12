
### An Overview of Clojure’s Evaluation Model
two steps   

+    reads textual source code, producing Clojure data structures. 
+   evaluate the data structures:  traverses the data structures and performs
actions like function application or var lookup based on the type of the data
structure.

In most languages the AST’s data structure is inaccessible within the
programming language; the programming language space and the compiler space are
forever separated, and never the twain shall meet.

But Clojure is different, because Clojure is a Lisp and Lisps are hotter than
a stolen tamale. Instead of evaluating an AST that’s represented as some
inaccessible internal data structure, Lisps evaluate native data structures.
Clojure still evaluates tree structures, but the trees are structured using
Clojure lists and the nodes are Clojure values.

Lists are ideal for constructing tree structures. The first element of a list is
treated as the root, and each subsequent element is treated as a branch. To
create a nested tree, you can just use nested lists.

### the reader  

The reader converts the textual source code you save in a file or enter in the
REPL into Clojure data structures. It’s like a translator between the human
world of Unicode characters and Clojure’s world of lists, vectors, maps,
symbols, and other data structures

 The textual representation of data structures is called a reader form.
**read-string** takes a string as an argument and processes it using Clojure’s
reader, returning a data structure.

    ```
    (read-string "(+ 1 2)")           ;(+ 1 2)
    (list? (read-string "(+ 1 2)"))   ;true
    (conj (read-string "(+ 1 2)") :test) ;(:test + 1 2)
    
    ```

In all the examples so far, there’s been a **one-to-one relationship** between
the reader form and the corresponding data structures. Here are more examples of
simple reader forms that directly map to the data structures they represent:

+   () A list reader form
+   str A symbol reader form
+   [1 2] A vector reader form containing two number reader forms
+   {:sound "hoot"} A map reader form with a keyword reader form and string
reader form

but there are read macros, allow you to represent data structures in more
compact way.

#### reader macros  

Reader macros are sets of rules for transforming text into data structures. They
often allow you to represent data structures in more compact ways because they
take an abbreviated reader form and expand it into a full form. They’re
designated by macro characters, like ' (the single quote), #, and @. 

    ```
    (read-string "'(a b c)")    ;(quote (a b c))
    (read-string "@var")        ;(clojure.core/deref var)
    (read-string "; ignore!\n(+ 1 2)")   ;(+ 1 2)
    ```

> Written with [StackEdit](https://stackedit.io/).
