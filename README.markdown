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

This doclet is implemented in the Groovy programming language. Javadoc will also need the Groovy runtime (e.g.
groovy-1.7.2.jar) in its classpath to execute the SpringDoclet.

### Options

SpringDoclet supports the following options to control its operation:

  + **-d** _directory_

    Specifies the output directory for the generated documentation. Defaults to the current directory.

  + **-f** _filename_

    Specifies the name of the output file for the generated documentation. Defaults to "spring-summary.html".

  + **-stylesheet** _path_

    Specifies the path to a stylesheet CSS file. This can be a relative or absolute path. By default, SpringDoclet
    creates its own stylesheet file named "spring-summary.css" in the same directory as the generated documentation.
    
  + **-linkpath** _path_

    Specifies the path to documentation for source code files (i.e. JavaDoc or JXR xref files). This should be set
    to the root of the directory containing the source code documentation HTML files. Defaults to the current directory.

### SpringDoclet with Maven

Follow the instructions in the Maven Javadoc plugin documentation to configure SpringDoclet as an alternate doclet:

<http://maven.apache.org/plugins/maven-javadoc-plugin/examples/alternate-doclet.html>

An example of using SpringDoclet with Maven is also included in the project. See the pom.xml file in the
[sample](http://github.com/scottfrederick/springdoclet/tree/master/sample) directory.

When using SpringDoclet with the Maven Javadoc plugin, the generated Maven site HTML will assume there is a file named
index.html in the output directory. You can use the "-f" option to override the default output file name to
"index.html" for compatibility with Maven site generation.

### SpringDoclet with Ant

Refer to the Ant Javadoc task documentation to configure SpringDoclet as an alternate doclet:

<http://ant.apache.org/manual/CoreTasks/javadoc.html>

An example of using SpringDoclet with Ant is also included in the project. See the build.xml file in the
[sample](http://github.com/scottfrederick/springdoclet/tree/master/sample) directory.

### SpringDoclet with the command line

Use the "-doclet" and "-docletpath" parameters to the Javadoc command-line tool to use SpringDoclet instead of the
standard doclet. Refer to the JDK documentation for more details.

## Building SpringDoclet

Before using SpringDoclet, you will need to build and deploy it.

To build SpringDoclet, use Maven from the top of the project.

  + To compile to a jar file (for use with Ant, the command line, etc), use "mvn assembly:assembly". This will
    create a single .jar file in the "target" directory of the project. The .jar file contains SpringDoclet and
    all dependent libraries.
  + To deploy to a local Maven repository, use "mvn install".

After building the doclet, you can test it using the "sample" directory in the project. 

## ToDos

There are a number of enhancements that will be made to the doclet, including:

  + Support additional Spring artifacts (e.g. @Autowired, @Aspect)

## License

This project is licensed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

