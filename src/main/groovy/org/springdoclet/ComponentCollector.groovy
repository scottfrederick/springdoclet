package org.springdoclet

import com.sun.javadoc.ClassDoc
import com.sun.javadoc.AnnotationDesc

class ComponentCollector implements Collector {
  private static String COMPONENT_TYPE = 'org.springframework.stereotype.'
  private componentsByType = [:]
  private File outputFile

  ComponentCollector(File outputFile) {
    this.outputFile = outputFile
  }

  void processClass(ClassDoc classDoc, AnnotationDesc[] annotations) {
    for (annotation in annotations) {
      def annotationType = Annotations.getTypeName(annotation)
      if (annotationType?.startsWith(COMPONENT_TYPE)) {
        def type = annotationType - COMPONENT_TYPE
        addComponent(classDoc, type)
      }
    }
  }

  private void addComponent(classDoc, type) {
    if (componentsByType[type] == null)
      componentsByType[type] = [classDoc.qualifiedTypeName()]
    else
      componentsByType[type] << classDoc.qualifiedTypeName()
  }

  void writeOutput() {
    outputFile << 'Components:\n'
    for (entry in componentsByType) {
      for (component in entry.value.sort()) {
        outputFile << "$entry.key: $component\n"
      }
      outputFile << "\n"
    }
  }
}
