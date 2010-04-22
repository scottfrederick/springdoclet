# SpringDoclet

SpringDoclet is Javadoc doclet that generates documentation on Spring artifacts in a project. The detection of
Spring artifacts is based on the presence of Spring annotations on Java classes and methods.

SpringDoclet currently detects and documents the following types of Spring artifacts:

  + **Components** - classes annotated with @Component, @Controller, @Repository, and @Service
  + **RequestMappings** - classes and methods annotated with @RequestMapping

SpringDoclet writes its output in HTML format into a file named **spring-summary.html**. The location of the file is
determined by the Javadoc "-d" option, with the same defaulting rules as the standard doclet.

## Using SpringDoclet

To use SpringDoclet, simply instruct the Javadoc tool to use this doclet instead of (or in addition to) the standard
doclet. The Javadoc tool needs to be provided with:

  + the name of the doclet class (**org.springdoclet.SpringDoclet**)
  + the jar file containing the doclet classes

In addition to the doclet classes, Javadoc needs access to the class files that implement the annotation types
used to detect Spring artifacts (e.g. @Controller, @RequestMapping). Make sure the necessary Spring jar files are
in the classpath used to run the Javadoc tool. If any annotation types cannot be located by Javadoc, SpringDoclet will
display an message like this in the Javadoc output:

    Unable to resolve annotation type '@Controller'; to fix this problem, add the class that implements
    the annotation type to the javadoc classpath

This message will be shown for all annotations found in the Java source but not found on the classpath. It can be 
ignored for all non-Spring annotations.

This doclet is implemented in the Groovy programming language. Javadoc will also need the Groovy runtime in its
classpath to execute the SpringDoclet.

### SpringDoclet with Maven

Follow the instructions in the Maven Javadoc plugin documentation to configure SpringDoclet as an alternate doclet:

<http://maven.apache.org/plugins/maven-javadoc-plugin/examples/alternate-doclet.html>

An example of using SpringDoclet with Maven is also included in the project in "sample/pom.xml".  

### SpringDoclet with Ant

Refer to the Ant Javadoc task documentation to configure SpringDoclet as an alternate doclet:

<http://ant.apache.org/manual/CoreTasks/javadoc.html>

### SpringDoclet with the command line

Use the "-doclet" and "-docletpath" parameters to the Javadoc command-line tool to use SpringDoclet instead of the
standard doclet. Refer to the JDK documentation for more details.
