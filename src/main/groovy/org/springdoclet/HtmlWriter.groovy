package org.springdoclet

import groovy.xml.MarkupBuilder

class HtmlWriter {
  void writeOutput(File outputFile, collectors) {
    def writer = new FileWriter(outputFile)
    def builder = new MarkupBuilder(writer)
    builder.html {
      head {
        title 'Spring Artifacts'
      }
      body {
        for (collector in collectors) {
          collector.writeOutput builder 
        }
      }
    }
  }
}
