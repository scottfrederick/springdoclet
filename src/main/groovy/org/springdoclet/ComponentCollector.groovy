package org.springdoclet

import com.sun.javadoc.ClassDoc
import com.sun.javadoc.AnnotationDesc
import groovy.xml.MarkupBuilder

class ComponentCollector implements Collector {
  private static String COMPONENT_TYPE = 'org.springframework.stereotype.'
  private componentsByType = [:]

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

  void writeOutput(MarkupBuilder builder) {
    builder.div(id: 'components') {
      h1 'Components'
      for (entry in componentsByType.sort()) {
        h2 entry.key
        for (component in entry.value.sort()) {
          p component
        }
      }
    }
  }
}
