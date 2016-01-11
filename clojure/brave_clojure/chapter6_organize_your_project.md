

### namespace   

**namespaces** are objects of type **clojure.lang.Namespace**, and you can
interact with them just like you can with Clojure data structures. 

    ```
    (ns-name *ns*)
    ```

when you give Clojure a **symbol** like ```map```, it finds the corresponding
**var** in the current namespace, gets a **shelf address**, and retrieves an
object from that shelf for you—in this case, the function that ```map``` refers
to. If you want to just use the symbol itself, and not the thing it refers to,
you have to **quote ** it.  **Quoting** any Clojure form tells Clojure not to
evaluate it but to treat it as data
    
    ```
    inc
    'inc
    (map inc [1 2])
    '(map inc [1 2])
    ```

#### Storing Objects with def

This process is called **interning** a var.

        ```
        (def great-books ["East of Eden" "The Glass Bead Game"])

        1)Update the current namespace’s map with the association between
great-books and the var.
        2)Find a free storage shelf.
        3)Store ["East of Eden" "The Glass Bead Game"] on the shelf.
        4)Write the address of the shelf on the var.
        5)Return the var (in this case, #'user/great-books).
        ```

 You can interact with a namespace’s map of symbols-to-interned-vars using
**ns-interns**.
    
    ```
    (ns-interns *ns*)
    (get (ns-interns *ns*) 'great-books)
    ```

```#'user/great-books``` is the **reader form** of a var.

```#'user/great-books``` lets you use the **var** associated with the symbol
```great-books``` within the user namespace. We can **deref*** vars to get the
objects they point to:

    ```
    (deref #'user/great-books)
    ```

#### Creating and Switching to Namespaces

**create-ns** takes a symbol, creates a namespace with that name if it doesn’t
exist already, and returns the namespace:

    ```
    (create-ns 'test.test-ns)
    ```
**in-ns**  creates the namespace if it doesn’t exist and switches to it

To use functions and data from other name­spaces, you can use a **fully
qualified symbol**. The general form is **namespace/name**.

+   refer   
    
    Calling refer with a namespace symbol lets you refer to the corresponding
namespace’s objects without having to use fully qualified symbols. It does this
by updating the current namespace’s symbol/object map.

    When you call refer, you can also pass it the filters **:only**,
**:exclude**, and **:rename**.    
    As the names imply, **:only** and **:exclude** restrict which symbol/var
mappings get merged into the current namespace’s **ns-map**. **:rename** lets
you use different symbols for the vars being merged in. 

Sometimes you may want a function to be available only to other functions within
the same namespace. Clojure allows you to define private functions using
**defn-**

+   alias

let you shorten a namespace name for using fully qualified symbols.

### Real Project Organization

+   require  

Do nothing if you’ve already called require with this symbol.Otherwise, find the
file that corresponds to this symbol,read and evaluate the contents of that
file. Clojure expects the file to declare a namespace corresponding to its path.

+   use (require + refer)


#### the ns Macro

refer the clojure.core namespace by default.

There are six possible kinds of references within ns:

+   (:refer-clojure)   
+   (:require)   
+   (:use)   
+   (:import)   
+   (:load)   
+   (:gen-class)   

> Written with [StackEdit](https://stackedit.io/).
