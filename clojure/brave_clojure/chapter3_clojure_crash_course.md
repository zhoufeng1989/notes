##lein command                
+   lein new app app_name  (create project structure)
+   lein run  (run current application)
+   lein uberjar  (make a jar)
+   lein repl (start repl)

## Repl  
Conceptually, the REPL is similar to Secure Shell (SSH). In the same way that
you can use SSH to interact with a remote server, the Clojure REPL allows you to
interact with a running Clojure process.

## Syntax  

### Form  
We use the term **form** to refer to valid code. I’ll also sometimes use
expression to refer to Clojure forms. Clojure **evaluates** every form to
produce a value. 

In other languages, different operations might have different structures
depending on the operator and the operands.Clojure’s structure is very simple
and consistent by comparison. No matter which operator you’re using or what kind
of data you’re operating on, the structure is the same.

### Control flow  
#### if 

    ```
    (if boolean-form
      then-form
      optional-else-form)
    ```

#### do  
The **do** operator lets you wrap up multiple forms in parentheses and run each
of them. 

    ```
    (if boolean-form
      (do then-form1
        then-form2)
      optional-else-form)

#### when   
The when operator is like **a combination of if and do**, but with no else
branch.Use when if you want to do multiple things when some condition is true,
and you always want to return nil when the condition is false.

#### Truthiness and boolean expression    
Both **nil** and **false** are used to represent logical falsiness, whereas all
other values are logically truthy. Truthy and falsey refer to how a value is
treated in a Boolean expression.

Clojure uses the Boolean operators **or** and **and**. or returns **either the
first truthy value or the last value**. and returns **the first falsey value or,
if no values are falsey, the last truthy value.**

### def   
def to bind a name to a value

## Data structures   

### map   

usually use keyword as the key

    ```
    {:first-name "feng" :last-name "zhou"}
    (hash-map :first-name "feng" :last-name "zhou")
    ```
    
+   get   

        ```
        (def name {:first-name "feng"  :last-name "zhou"}
        (get fullname :first-name)     ;"feng"
        (get fullname :middle-name)    ;null
        (get fullname :middle-name "default")   ;default
        ```
    

+   get-in(look up values in nested maps)

        ```
        (get-in {:b {:c [1 2 3]} :d "a"} [:b :c])          ;[1, 2, 3]
        ```

+   Another way to look up a value in a map is to treat the map like a function
with the key as its argument

    ```
    ({:a 1 :b 2} :a)         ;1
    ({:a 1 :b 2} :c)         ;nil
    ```
    

### keyword(start with :)

Keywords can be used as functions that look up the corresponding value in a data
structure.

    ```
    (:a {:a 1 :b 2})             ;1
    (:c {:a 1 :b 2})             ;nil
    ```

### vector  

    ```
    [1 2 3]               ;[1 2 3]
    (vector 1 2 3)        ;[1 2 3]
    ```
    
+   get(same as map)

        ```
        (get [3 2 1] 0)                               ;3
        ```

+   conj (add additional elements to the vector)

        ```
        (conj [1 2 3] 4)                            ;[1 2 3 4]
        ```
    
### list

Lists are similar to vectors in that they’re linear collections of values.But
there are some differences. For example, you can’t retrieve list elements with
get.    

    ```
    '(1 2 3 4)     ;(1 2 3 4)
    (list 1 2 3 4)     ;(1 2 3 4)
    ```

+   nth( not same as map using get)  

        ```
        (nth '(1 2 3 4) 3)         ;4
        ```

    **performace tips**    
    using nth to retrieve an element from a list is slower than using get to
retrieve an element from a vector. This is because Clojure has to traverse all
n elements of a list to get to the nth, whereas it only takes a few hops at most
to access a vector element by its index.
    
+   conj(add element at the beginning of list, not append)    

        ```
        (conj '(1 2 3) 4)        ;(4 1 2 3)
        ```
            
#### set (collection of unique value, hash set and sort set)

    ```
    #{1 2 3}                      ;#{1 2 3}
    (hash-set 1 2 3 2)            ;#{1 2 3}
    (set [1 2 1 2])               ;from vector #{1 2}
    ```

+   get, keyword, contains?

    **contains?** returns true or false, whereas **get** and **keyword** lookup
will return the value if it exists, or nil if it doesn’t.

        ```
        (contains? #{:a nil} :a)                   ;true
        (contains? #{:a nil} :b)                   ;false
        (get #{:a nil}   :nil)                         ;nil
        (:a #{:a nil})                                    :a
        ```
+   conj

        ```
        (conj #{1 2} 3)                                    ;#{1 2 3}
        ```

Notice that using get to test whether a set contains nil will always return nil,
which is confusing. contains? may be the better option when you’re testing
specifically for set membership.

#### simplicity

It is better to have 100 functions operate on one data structure than 10
functions on 10 data structures.Use built-in data structures first!

### function

#### Function Calls, Macro Calls, and Special Forms

The main feature that makes special forms “special” is that, unlike function
calls, they don’t always evaluate all of their operands.
 
Another feature that differentiates special forms is that you can’t use them as
arguments to functions. 

**In general, special forms implement core Clojure functionality that just can’t
be implemented with functions.** Clojure has only a handful of special forms,
and it’s pretty amazing that such a rich language is implemented with such
a small set of building blocks.

Macros are similar to special forms in that they evaluate their operands
differently from function calls, and they also can’t be passed as arguments to
functions.



#### define function    

Function definitions are composed of five main parts:

+   defn
+   Function name
+   A docstring describing the function (optional)
+   Parameters listed in brackets
+   Function body

**docstring**

The docstring is a useful way to describe and document your code. You can view
the docstring for a function in the REPL with (doc fn-name).


**arity overloading**   
  
Functions also support arity overloading. This means that you can define
a function so a different function body will run depending on the arity.

    ```
    (defn greeting
      ([name] (str "hello:" name))
      ([] (greeting "world"))
      )
    ```
Clojure also allows you to define **variable-arity** functions by including
a rest parameter, as in “put the rest of these arguments in a list with the
following name.” The rest parameter is indicated by an *ampersand* (&), 
You can mix rest parameters with normal parameters, but the rest parameter has
to come last.

    ```
    (defn favorite-things
      [name & things]
      (str "hello, " name ", my favorite things are " (clojure.string/join ",
" things))
      )
    ```

**Destructuring**

The basic idea behind destructuring is that it lets you concisely bind names to
values within a collection. 

You can also destructure maps. You can retain access to the original map
argument by using the **:as** keyword.

    ```
    (defn my-first
      [[first-thing]]
      first-thing)

    (defn get-location
      [{lat :lat lng :lng}]
      (println (str "lat is: " lat))
      (println (str "lng is: " lng))
      )

    (defn get-location2
      [{:keys [lat lng] :as my-location}]
      (println (str "lat is: " lat))
      (println (str "lng is: " lng))
      (println my-location)
      )
      ```

**all functions are created equal**     
Clojure has no privileged functions. + is just a function, - is just a function,
and inc and map are just functions. They’re no better than the functions you
define yourself. So don’t let them give you any lip!

#### Anonymous Functions

    ```
    (fn [param-list]
      function body)

    #(op % param-list)
    ```
    
    ```
     ((fn [name] (str "hello, " name)) "world")
     (#(str "hello, " %) "world")
    ```

### Others

#### let

let forms have two main uses. First, they provide clarity by allowing you to
name things. 

Second, they allow you to evaluate an expression only once and reuse the result.
This is especially important when you need to reuse the result of an expensive
function call, like a network API call. It’s also important when the expression
has side effects.
    
#### loop

    ```
    (loop [iteration 0]
      (println (str "iteration: " iteration))
      (if (> iteration 3)
    (println "goodbye")
    (recur (inc iteration))
    )
    )
    ```


> Written with [StackEdit](https://stackedit.io/).
