##retrieving attribute from object##

+   If attrname is a special (i.e. Python-provided) attribute for objectname, return it.

+   Check objectname.__class__.__dict__ for attrname. If it exists and is a data-descriptor, return the descriptor result. Search all bases of objectname.__class__ for the same case.

+   Check objectname.__dict__ for attrname, and return if found. If objectname is a class, search its bases too. If it is a class and a descriptor exists in it or its bases, return the descriptor result.

+   Check objectname.__class__.__dict__ for attrname. If it exists and is a non-data descriptor, return the descriptor result. If it exists, and is not a descriptor, just return it. If it exists and is a data descriptor, we shouldn't be here because we would have returned at point 2. Search all bases of
objectname.__class__ for same case.

+   Raise AttributeError

**__getattr__ and __getattribute__**

__getattr__ is called at lowest priority, while __getatttribute__ is called whenever the attribute is accessed. Therefore, __getattribute__ will make the speed slower.

## setting a user-defined attribute ##

+   Check objectname.__class__.__dict__ for attrname. If it exists and is a data-descriptor, use the descriptor to set the value. Search all bases of objectname.__class__ for the same case.
+   Insert something into objectname.__dict__ for key "attrname".

set attribute on class will override descriptor.

**__setattr__**

__setattr__ is the same as __getattribute__ in the triggering mechanism -- it intercepts the assignment operation no matter the attribute to modify already exists or not.

##descriptor##

**what is a descriptor**

In general, a descriptor is an object attribute with “binding behavior”, one whose attribute access has been overridden by methods in the descriptor protocol.   
Those methods are __get__(), __set__(), and __delete__().    
If any of those methods are defined for an object, it is said to be a descriptor.   


**descriptor protocol**

```
descr.__get__(self, obj, type=None) --> value

descr.__set__(self, obj, value) --> None

descr.__delete__(self, obj) --> None
```

obj is the object on which the attribute is accessed, type is the type(class) of object.


**invoking descriptor**

For objects, the machinery is in object.__getattribute__() which transforms b.x into type(b).__dict__['x'].__get__(b, type(b)).   
The implementation works through a precedence chain that gives data descriptors priority over instance variables, instance variables priority over non-data descriptors, and assigns lowest priority to __getattr__() if provided. 

For classes, the machinery is in type.__getattribute__() which transforms B.x into B.__dict__['x'].__get__(None, B).

some tips   
+   descriptors are invoked by the __getattribute__() method
+   overriding __getattribute__() prevents automatic descriptor calls
+   __getattribute__() is only available with new style classes and objects
+   object.__getattribute__() and type.__getattribute__() make different calls to __get__().
+   data descriptors always override instance dictionaries.
+   non-data descriptors may be overridden by instance dictionaries. 

**properity**  
Calling property() is a succinct way of building a data descriptor that triggers function calls upon access to an attribute. Its signature is:

```
property(fget=None, fset=None, fdel=None, doc=None) -> property attribute
```

properity pure python equivalant

```
class Property(object):
    "Emulate PyProperty_Type() in Objects/descrobject.c"

    def __init__(self, fget=None, fset=None, fdel=None, doc=None):
        self.fget = fget
        self.fset = fset
        self.fdel = fdel
        if doc is None and fget is not None:
            doc = fget.__doc__
        self.__doc__ = doc

    def __get__(self, obj, objtype=None):
        if obj is None:
            return self
        if self.fget is None:
            raise AttributeError("unreadable attribute")
        return self.fget(obj)

    def __set__(self, obj, value):
        if self.fset is None:
            raise AttributeError("can't set attribute")
        self.fset(obj, value)

    def __delete__(self, obj):
        if self.fdel is None:
            raise AttributeError("can't delete attribute")
        self.fdel(obj)

    def getter(self, fget):
        return type(self)(fget, self.fset, self.fdel, self.__doc__)

    def setter(self, fset):
        return type(self)(self.fget, fset, self.fdel, self.__doc__)

    def deleter(self, fdel):
        return type(self)(self.fget, self.fset, fdel, self.__doc__)
```
