package org.springdoclet

interface Collector {
  void processClass(classDoc, annotations)
  void writeOutput()
}