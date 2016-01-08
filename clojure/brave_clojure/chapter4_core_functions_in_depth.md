### Programming to Abstractions

Clojure defines map and reduce functions in terms of the **sequence
abstraction**, not in terms of specific data structures. As long as a data
structure responds to the core sequence operations (the functions first, rest,
and cons), it will work with map, reduce, and oodles of other sequence functions
for free. This is what Clojurists mean by programming to abstractions, and it’s
a central tenet of Clojure philosophy.

If the core sequence functions first, rest, and cons work on a data structure,
you can say the data structure **implements the sequence abstraction**. 


####Abstraction Through Indirection

function is able to work with different data structures,
Clojure does this using two forms of **indirection**.   

In programming, indirection is a generic term for the mechanisms a language
employs so that one name can have multiple, related meanings. 

When it comes to sequences, Clojure also creates indirection by doing a kind of
lightweight type conversion, producing a data structure that works with an
abstraction’s functions. Whenever Clojure expects a sequence—for example, when
you call map, first, rest, or cons—it calls the seq function on the data
structure in question to obtain a data structure that allows for first, rest,
and cons

#### abstraction and implementation  

it’s powerful to focus on what we can do with a data structure and to ignore, as
much as possible, its implementation. Implementations rarely matter in and of
themselves. They’re just a means to an end. In general, programming to
abstractions gives you power by letting you use libraries of functions on
different data structure regardless of how those data structures are
implemented.

### Seq function examples

#### map

    ````
    (def sum #(reduce + %))
    (def avg #(/ (sum %) (count %)))
    (defn stats
      [numbers]
      (map #(% numbers) [sum count avg])
     )
      ```
      
Additionally, Clojurists often use map to retrieve the value associated with
a keyword from a collection of map data structures. Because keywords can be used
as functions, you can do this succinctly.

#### reduce
    ```
    (reduce (fn [new-map [key value]]
          (assoc new-map key (inc value))
          )
        ()
        {:max 20 :min 1}
        )
       ```
> Written with [StackEdit](https://stackedit.io/).
