PaxPlay
==========
This is a simple playground for testing out new patterns, technologies with the objective to create clean, simple and
maintainable services.

REST Framework
--------------
At the moment, the project includes a simple REST framework based on a Jetty Servlet together with a sample test resource.
The project aims to address the following topics related to creating a clean REST API:

* URL schema - Patterns for routing and connecting request URL and HTTP method to a Java method 
* API Versioning - ability to support multiple, non-backwards compatible versions in parallel
* Data marshalling - clean/maintainable translation from JSON/XML to business logic and back
* Error handling - Map exceptions to error codes, error messages, etc. 

Setup
-------

PaxLab strives to be as free as possible from heavy frameworks and bloated tools but tight and focused tools that are
excellent at solving a particular problem are of course useful. PaxPlay is currently dependant on the tools/frameworks
listed below. These need to be on the classpath in order to start the server.

* [GSON](http://eclipse.org/jetty/)
* [Jetty](http://eclipse.org/jetty/)

