## what is apache spark

Apache Spark is a cluster computing platform designed to be fast and general-purpose.  

## A Unified Stack

The Spark project contains multiple closely integrated components. At its core,
Spark is a “computational engine” that is responsible for scheduling,
distributing, and monitoring applications consisting of many computational
tasks across many worker machines, or a computing cluster. Because the core
engine of Spark is both fast and general-purpose, it powers multiple
higher-level components specialized for various workloads, such as SQL or
machine learning. These components are designed to interoperate closely, letting
you combine them like libraries in a software project.

A philosophy of tight integration has several benefits.  
First, all libraries and higher-level components in the stack benefit from improvements at the lower
layers. For example, when Spark’s core engine adds an optimization, SQL and
machine learning libraries automatically speed up as well.   
Second, the costs associated with running the stack are minimized, because instead of running 5–10
independent software systems, an organization needs to run only one. These costs
include deployment, maintenance, testing, support, and others. This also means
that each time a new component is added to the Spark stack, every organization
that uses Spark will immediately be able to try this new component. This changes
the cost of trying out a new type of data analysis from downloading, deploying,
and learning a new software project to upgrading Spark.   
Finally, one of the largest advantages of tight integration is the ability to
build applications that seamlessly combine different processing models.
