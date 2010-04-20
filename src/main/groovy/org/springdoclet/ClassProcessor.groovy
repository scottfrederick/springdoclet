package org.springdoclet

import com.sun.javadoc.ClassDoc

class ClassProcessor {
  Collector[] collectors

  ClassProcessor(Collector[] collectors) {
    this.collectors = collectors
  }

  void process(ClassDoc[] classes) {
    collectors.each { processClasses(classes, it) }
  }

  private void processClasses(ClassDoc[] classes, Collector collector) {
    for (classDoc in classes) {
      def annotations = classDoc.annotations()
      if (annotations) {
        collector.processClass(classDoc, annotations)
      }
    }

    collector.writeOutput()
  }
}
