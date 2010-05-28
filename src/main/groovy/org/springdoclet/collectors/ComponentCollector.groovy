package org.springdoclet.collectors

import com.sun.javadoc.ClassDoc
import com.sun.javadoc.AnnotationDesc
import groovy.xml.MarkupBuilder
import org.springdoclet.Collector
import org.springdoclet.Annotations
import org.springdoclet.PathBuilder
import org.springdoclet.TextUtils

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
    def component = [className: classDoc.qualifiedTypeName(), text: TextUtils.getFirstSentence(classDoc.commentText())]
    if (componentsByType[type] == null)
      componentsByType[type] = [component]
    else
      componentsByType[type] << component
  }

  void writeOutput(MarkupBuilder builder, PathBuilder paths) {
    builder.div(id: 'components') {
      h2 'Components'
      for (entry in componentsByType.sort()) {
        h3 entry.key
        table(id:entry.key) {
/*
          tr {
            th 'Class'
            th 'Description'
          }
*/
          def sortedComponents = entry.value.sort { it.className }
          for (component in sortedComponents) {
            tr {
              td {
                a(href: paths.buildFilePath(component.className), component.className)
              }
              td { code { mkp.yieldUnescaped(component.text ?: ' ') } }
            }
          }
        }
      }
    }
  }
}
