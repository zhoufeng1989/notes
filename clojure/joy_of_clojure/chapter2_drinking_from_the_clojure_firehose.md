## Scalar

Clojure provides several different categories of scalar data types: integers,
floats, rationals, symbols, keywords, strings, characters, booleans, and regex
patterns.

**floating-point values** are arbitrarily precise
    >>
        (+ 0.1 0.2) == 0.3

**Rational numbers** offer a more compact and precise representation of a given
value over floating-point.
    >>
        (+ 7/11 4/11) == 1

**Symbols** in Clojure are objects in their own right, but are often used to
represent another value. When a number or a string is evaluated, you get back
exactly the same object, but when a symbol is evaluated, you’ll get back
whatever value that symbol is referring to in the current context.

    >>
    (def a 1)
    (eval 'a) == 1

**Keywords** are similar to symbols, except that they always evaluate to
themselves.Although keywords are prefixed by a colon :, it’s only part of the
literal syntax and not part of the name itself. 

Clojure **characters** are written with a literal syntax prefixed with
a backslash and are stored as Java Character objects.

    >>
    \a ; character a

## collections

Literal **lists** are written with parentheses.

    >>
    (a b c)

When a list is **evaluated**, the first item of the listwill be resolved to
a function, macro, or special form.

when evaluated, **vectors **simply evaluate each item in order. No function or
macro call is performed on the vector itself, though if a list appears within
the vector, that list is evaluated following the normal rules for a list.

**map**, **set**

## functions

### calling functions  
 The immediately obvious advantage of **prefix over infix notation** used by
C-style languages is that the former allows any number of operands per operator,
whereas infix allows only two. Another, less obvious advantage to structuring
code as prefix notation is that it completely **eliminates the problem of
operator precedence**. 

### define functions

+   fn

        >>
        (fn sum [x y] (+ x y))

This is far from satisfying, given that now that the function has been defined,
there’s no apparent way to execute it.     
It should be noted that the **sum** symbol is optional and doesn’t correspond to
a globally accessible name for the function, but instead to a name internal to
the function itself used for self-calls. 

+   fn without name   
        
        >>
        ((fn [x y] (+ x y)) 1 2)
        
This form to define functions allows for **arity overloading** of the
invocations of a function.

    >>
    (fn 
        ([x y] (+ x y))
        ([x y z] (+ x y z))
    )
**variable arguments**  
The way to denote variable arguments is to use the **&** symbol followed by
a symbol. Every symbol in the arguments list before the & will still be bound
one-for-one to the same number of arguments passed during the function call. But
any additional arguments will be aggregated in a sequence bound to the symbol
following the & symbol.
    
    >>
    ((fn [x y & z] [x y z]) 1 2 3 4)
    => [1 2 (3 4)]
    
    >>
    ((fn [x y & z] [x y z]) 1 2)
    => [1 2 nil]

+   def   
The def special form is a way to assign a symbolic name to a piece of Clojure
data.

    >>
    (def sum 
        (fn [x y] (+ x y))
    )

+   defn   

    >>
    (defn sum [x y] (+ x y))

+   in place function with #()

Clojure provides a shorthand notation for creating an anonymous function using
the #() reader feature. In a nutshell, **reader features** are analogous to
preprocessor directives in that they signify that some given form should be
replaced with another at read time. In the case of the #() form, it’s
effectively replaced with the special form fn. In fact, anywhere that it’s
appropriate to use #(), it’s likewise appropriate for the fn special form.

    >>
    (def sum #(+ %1 %2 %3))
    (sum 1 2 3) 
    => 6
