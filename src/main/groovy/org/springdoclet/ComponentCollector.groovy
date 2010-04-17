package org.springdoclet

class ComponentCollector {
  private static String COMPONENT_TYPE = 'org.springframework.stereotype.'
  private componentsByType = [:]

  void processClass(classDoc, annotations) {
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

  String toString() {
    def str = new StringBuffer("Components:\n")
    for (entry in componentsByType) {
      for (component in entry.value.sort()) {
        str << "$entry.key: $component\n"
      }
      str << "\n"
    }
    return str
  }
}
