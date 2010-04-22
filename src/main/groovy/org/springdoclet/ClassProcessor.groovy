package org.springdoclet

import com.sun.javadoc.AnnotationDesc

class ClassProcessor {
  void process(classes, collectors) {
    collectors.each { processClasses(classes, it) }
  }

  private void processClasses(classes, collector) {
    for (classDoc in classes) {
      AnnotationDesc[] annotations = classDoc.annotations()
      if (annotations) {
        collector.processClass(classDoc, annotations)
      }
    }
  }
}
