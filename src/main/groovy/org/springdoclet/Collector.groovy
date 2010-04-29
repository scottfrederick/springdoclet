package org.springdoclet

import com.sun.javadoc.ClassDoc
import com.sun.javadoc.AnnotationDesc
import groovy.xml.MarkupBuilder

interface Collector {
  void processClass(ClassDoc classDoc, AnnotationDesc[] annotations)
  void writeOutput(MarkupBuilder builder, PathBuilder paths)
}