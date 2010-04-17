package org.springdoclet

import com.sun.javadoc.ClassDoc

class ClassProcessor {
  def collectors = []

  void process(ClassDoc classDoc) {
    def annotations = classDoc.annotations()
    if (annotations) {
      collectors.each { collector -> collector.processClass(classDoc, annotations) }
    }
  }

  String toString() {
    return collectors.toString()
  }
}
