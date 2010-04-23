package org.springdoclet

import com.sun.javadoc.AnnotationDesc
import com.sun.javadoc.ClassDoc
import groovy.xml.MarkupBuilder

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
          processMethod classDoc, rootPath, defaultHttpMethods, annotation
        }
      }
    }
  }

  private def processMethod(classDoc, rootPath, defaultHttpMethods, annotation) {
    def (path, httpMethods) = getMappingElements(annotation)
    for (httpMethod in (httpMethods ?: defaultHttpMethods)) {
      addMapping classDoc, "$rootPath$path", httpMethod
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

  private void addMapping(classDoc, path, httpMethod) {
    def httpMethodName = httpMethod.toString() - METHOD_TYPE
    mappings << [path: path, httpMethodName: httpMethodName, className: classDoc.qualifiedTypeName()]
  }

  void writeOutput(MarkupBuilder builder) {
    builder.div(id:'request-mappings') {
      h1 'Request Mappings'
      table {
        def sortedMappings = mappings.sort { it.path }
        for (mapping in sortedMappings) {
          tr {
            td mapping.httpMethodName
            td mapping.path
            td mapping.className
          }
        }
      }
    }
  }
}
