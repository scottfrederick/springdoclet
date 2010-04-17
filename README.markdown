# SpringDoclet

SpringDoclet is Javadoc doclet that generates documentation on Spring artifacts in a project. The detection of
Spring artifacts is based on the presence of Spring annotations on Java classes and methods.

SpringDoclet currently detects and documents the following types of Spring artifacts:

  + **Components** - classes annotated with @Component, @Controller, @Repository, and @Service
  + **RequestMappings** - classes and methods annotated with @RequestMapping

SpringDoclet writes its output in HTML format int a file named spring-summary.html. The location of the file is
determined by the Javadoc "-d" option, with the same defaulting rules as the standard doclet.

## Using SpringDoclet

### With the command line

### With Ant

Refer to the Ant Javadoc task documentation to configure SpringDoclet as an alternate doclet:

[http://ant.apache.org/manual/CoreTasks/javadoc.html]

### With Maven

Follow the instructions in the Maven Javadoc plugin documentation to configure SpringDoclet as an alternate doclet:

[http://maven.apache.org/plugins/maven-javadoc-plugin/examples/alternate-doclet.html]

## Troubleshooting

### Annotation types in the Javadoc classpath
The Javadoc tool must have the class files for all annotation types that are processed by SpringDoclet
(i.e. @Controller, @RequestMapping, etc). If any annotation type cannot be located by Javadoc, you will see a messsage
like this in the Javadoc output:

    Unable to resolve annotation type '@Controller'; to fix this problem, add the class that implements
    the annotation type to the javadoc

This message will be shown for all annotations found in the Java source. It can be ignored for all non-Spring
annotations.