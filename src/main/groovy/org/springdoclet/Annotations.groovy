package org.springdoclet

import com.sun.javadoc.AnnotationDesc

class Annotations {
  private static unresolvedAnnotations = []

  static String getTypeName(AnnotationDesc annotation) {
    try {
      return annotation.annotationType().toString()
    } catch (java.lang.ClassCastException e) {
      if (!unresolvedAnnotations.contains(annotation.toString())) {
        reportError(annotation)
        unresolvedAnnotations << annotation.toString() 
      }
      return null
    }
  }

  private static void reportError(annotation) {
    ErrorReporter.printWarning "Unable to resolve annotation type '$annotation'; " +
            "to fix this problem, add the class that implements the annotation type to the javadoc classpath"
  }
}
