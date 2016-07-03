

# JavaScript Objects 
An object is just a container for a collection of named values(aka properties). 

## Constructor 
The role of constructor function is to create objects that share certain
qualities and behaviors. When a function is invoked using the **new** keyword,
the function sets the value of **this** for the function to the new object that
is being constructed and the function will return the newly created object by
default. 

The nine native object constructors that come prepackaged with JavaScript are:
        **Number**,**String**,**Boolean**,**Object**,**Array**,**Function**,**Date**,**RegExp**,**Error**.

Notes: **Math** is just an object namespace set up by JavaScript to house math
functions. 

## Primitive value and Complex object 
For **number**, **string** and **boolean**, if you call the constructors
directly, then a complex object is returned. If you simply express a number,
string or Boolean value in code, then the constructor will return a primitive
value instead of a complex object value.  When using literal values, an actual
object is never created until the value is treated as an object. 

## Prototype property 
**instanceof** only works with complex objects and instances created from
constructor functions that return objects.(not works for primitive value). 

**typeof**
    
    ```
    typeof new String("abc")  # object
    typeof "abc" # string 
    typeof new Number(123) # object
    typeof 123   # number
    ```

All object instances have a property that is a link (aka \_\_proto__)to the
constructor function that created the instance. ( the prototype property of the
constructor function). 

Since all prototype properties are objects, the final link in the chain is
**Object.prototype** and thus all of the properties and methods of **Object()**
are inherited by all JavaScript objects.

## in and hasOwnProperty  
While the **in** operator can check for properties of an object, including
properties from the prototype chain, the **hasOwnProperty** method can check an
property for a property that is not from the prototype chain. 

## Boolean 
Any valid JavaScript value that is not **0, -0, null, false, NaN, undefined or
empty string("")** will be converted to **true**.
Non-primitive false boolean objects convert to true. 

## undefined  
+    a declared variable has no assigned value.
+    an object property you're trying to access is not defined and is not found
in the prototype chain.  

## head object 
User-defined code is placed by JavaScript inside the head object for execution. 
Two ways to reference the head object: user the name (eg, window) and use the
**this** keyword in the global scope. 

## function
the **arguments** object is an array-like object containing all of the
parameters being passed to the function.  the argments has a property called
**callee**, which is a reference to the function currently executing. 

The value of this is based on the context in which the function is being called. 

### invoke a function 
+    as a function, **this** is the global object.
+    as a method, **this** is the object contained the function.
+    as a constructor, **this** is the newly created object.
+    using apply() or call(), **this** is the first parameter of the function
call. 

## Scope 
three kind of scopes, global scope, local scope and eval scope. The global scope
is the last stop in the scope chain.  
JavaScript does not have block scope. Scope is determined during function
definition, not invocation(lexical scoping).



> Written with [StackEdit](https://stackedit.io/).
