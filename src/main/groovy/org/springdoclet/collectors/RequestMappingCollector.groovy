package org.springdoclet.collectors

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import groovy.xml.MarkupBuilder
import org.springdoclet.Collector
import org.springdoclet.Annotations
import org.springdoclet.PathBuilder
import org.springdoclet.TextUtils

@SuppressWarnings("GroovyVariableNotAssigned")
class RequestMappingCollector implements Collector {
  private static String MAPPING_TYPE = 'org.springframework.web.bind.annotation.RequestMapping'
  private static String METHOD_TYPE = 'org.springframework.web.bind.annotation.RequestMethod.'

  private mappings = []

  void processClass(ClassDoc classDoc, AnnotationDesc[] annotations) {
    def annotation = getMappingAnnotation(annotations)
    if (annotation) {
      def rootPath, defaultHttpMethods
      (rootPath, defaultHttpMethods) = getMappingElements(annotation)
      processMethods classDoc, rootPath ?: "", defaultHttpMethods ?: ['GET']
    } else {
      processMethods classDoc, "", ['GET']
    }
  }

  private void processMethods(classDoc, rootPath, defaultHttpMethods) {
    def methods = classDoc.methods(true)
    for (method in methods) {
      for (annotation in method.annotations()) {
        def annotationType = Annotations.getTypeName(annotation)
        if (annotationType?.startsWith(MAPPING_TYPE)) {
          processMethod classDoc, method, rootPath, defaultHttpMethods, annotation
        }
      }
    }
  }

  private def processMethod(classDoc, methodDoc, rootPath, defaultHttpMethods, annotation) {
    def (path, httpMethods) = getMappingElements(annotation)
    for (httpMethod in (httpMethods ?: defaultHttpMethods)) {
      addMapping classDoc, methodDoc, "$rootPath$path", httpMethod
    }
  }

  private def getMappingAnnotation(annotations) {
    for (annotation in annotations) {
      def annotationType = Annotations.getTypeName(annotation)
      if (annotationType?.startsWith(MAPPING_TYPE)) {
        return annotation
      }
    }
    return null
  }

  private def getMappingElements(annotation) {
    def elements = annotation.elementValues()
    def path = getElement(elements, "value") ?: ""
    def httpMethods = getElement(elements, "method")?.value()
    return [path, httpMethods]
  }

  private def getElement(elements, key) {
    for (element in elements) {
      if (element.element().name() == key) {
        return element.value()
      }
    }
    return null
  }

  private void addMapping(classDoc, methodDoc, path, httpMethod) {
    def httpMethodName = httpMethod.toString() - METHOD_TYPE
    mappings << [path: path,
            httpMethodName: httpMethodName,
            className: classDoc.qualifiedTypeName(),
            text: TextUtils.getFirstSentence(methodDoc.commentText())]
  }

  void writeOutput(MarkupBuilder builder, PathBuilder paths) {
    builder.div(id:'request-mappings') {
      h2 'Request Mappings'
      table {
        def sortedMappings = mappings.sort { it.path }
        tr {
          th 'Method'
          th 'URL Template'
          th 'Class'
          th 'Description'
        }
        for (mapping in sortedMappings) {
          tr {
            td mapping.httpMethodName
            td mapping.path
            td {
              a(href: paths.buildFilePath(mapping.className), mapping.className)
            }
            td { code { mkp.yieldUnescaped(mapping.text ?: ' ') } }
          }
        }
      }
    }
  }
}
