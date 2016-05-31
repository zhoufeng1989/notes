## The Classification of Exceptions  

![Java Exception hierarchy](exception-hierarchy.png)

### Error  
The **Error** hierarchy describes internal errors and resource exhaustion situations inside the Java runtime system. You
should not throw an object of this type. There is little you can do if such an internal error occurs, beyond notifying 
the user and trying to terminate the program gracefully.

### Exception 
The Exception hierarchy splits into two branches: exceptions that derive from **RuntimeException** and those that do not.
The general rule is this: A **RuntimeException** happens because you made a programming error. Any other exceptions 
occurs because a bad thing, such as an I/O error, happened to your otherwise good program. If it is a RuntimeException, 
it was your fault. 


### checked exception and unchecked exception 
The Java Language Specification calls any exception that derives from the class **Error** or the class **RuntimeException** 
an **unchecked** exception. All other exceptions are called **checked exceptions**. The compiler checks that you provide 
exception handlers for all checked exceptions.  

A method must declare all the **checked exceptions** that it might throw. Unchecked exceptions are either beyond your 
control (Error) or result from conditions that you should not have allowed in the first place(RuntimeException). Instead 
of declaring the exception, you can also catch it. Then the exception won't be thrown out of the method, and no throws 
specification is necessary. 

If you override a method from a superclass, the checked exceptions that the subclass method declares cannot be more general 
than those of the superclass method.(It is OK to throw more specific exceptions or not to throw any exceptions in the 
subclass method.) If the superclass method throws no checked exception at all, neither can the subclass.

### throws an exception

```throw new ExceptionClass()``` or ```throw new ExceptionClass(msg)```

If you want to change the exception type, rethrow it in catch block. It is a good practise to set original exception as 
the "cause" of the new exception: 

    ```
    try {
        access the database
    }
    catch (SQLException e) {
        Throwable se = new ServletException("database error");
        // The original exception can be retrieved as: se.getCause();
        se.initCause(e);
        throw se;
    }
    ```
    
### finally  
a finally clause can yield unexpected results when it contains return statement or throws an exception.  
    ```
    try {
        ....
        // If finally return a value, return in try statement will be ignored.
        return 10;
    }
    catch (Exception e) {
        //If finally throw an exception, the exception in catch is ignored.
        throw e;
    }
    finally {
        io.close();
        //return 100;
    }
    ```

### Try-with-Resources Statement 
Provided the resource belongs to a class that implements the ```AutoCloseable``` interface, when the try block exists, 
the resource.close() is called automatically. This handles the difficulty when the try block throws an exception and the 
close method also throws an exception. 

### Stack Trace 
You can acess the text description of a stack trace by calling the ```printStackTrace``` method of the Throwable class. 
```getStackTrace``` method yields an array of StackTraceElement objects

### Assertion  
enable assertions ```java -enableassertions MyApp```  

