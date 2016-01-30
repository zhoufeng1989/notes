## Object-Oriented Metaphysics
 The object has properties that may change over time, but it’s still treated as
a single, constant object. 

The fact that objects are never stable doesn’t stop us from treating them as the
fundamental building blocks of programs. In fact, this is considered an
advantage of OOP. It doesn’t matter how the state changes; you can still
interact with a stable interface and everything will work as it should. This
conforms to our intuitive sense of the world. A piece of wax is still the same
piece of wax even if its properties change: if I change its color, melt it, and
pour it on the face of my enemy, I’d still think of it as the same wax object
I started with.


## Clojure Metaphysics
### Values
The term value is used often by Clojurists, and its specific meaning might
differ from what you’re used to. Values are atomic in the sense that they form
a single irreducible unit or component in a larger system; they’re indivisible,
unchanging, stable entities.

So a value doesn’t change, but you can apply a process to a value to produce
a new value.

This leads to a different conception of identity. Instead of understanding
identity as inherent to a changing object, as in OO metaphysics, Clojure
metaphysics construes identity as something we humans impose on a succession of
unchanging values produced by a process over time. We use names to designate
identities.

**Values can not be changed, but identity can associate with new value**:
These values don’t act on each other, and they can’t be changed. They can’t do
anything. Change only occurs when a) a process generates a new value and b) we
choose to associate the identity with the new value.

## Atom

Atom reference type allows you to endow a succession of related values with an
identity.

**swap!**
To update the atom so that it refers to a new state, you use ```swap!```. The
atomic values don’t change, but the reference type can be updated and assigned
a new value.

    ```
    (def a (atom {:x 1 :y 2}))
    @a
    (swap! a (fn [current-state] (assoc current-state :z 3)))
    @a

    (swap! a update-in [:x] + 10)
    @a
    ```

```swap!``` implements compare-and-set semantics, meaning it does the following
internally:

+   It reads the current state of the atom.
+   It then applies the update function to that state.
+   Next, it checks whether the value it read in step 1 is identical to the
atom’s current value.
+   If it is, then swap! updates the atom to refer to the result of step 2.
+   If it isn’t, then swap! retries, going through the process again with step
1.

One detail to note about swap! is that atom updates happen synchronously; they
will block their thread.

To recap: atoms implement Clojure’s concept of state. They allow you to endow
a series of immutable values with an identity. They offer a solution to the
**reference cell and mutual exclusion** problems through their compare-and-set
semantics. They also allow you to work with past states without fear of them
mutating in place.
> Written with [StackEdit](https://stackedit.io/).
