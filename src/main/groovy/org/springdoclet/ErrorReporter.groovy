package org.springdoclet

import com.sun.javadoc.DocErrorReporter

class ErrorReporter {
  private static DocErrorReporter delegate

  static setErrorReporter(DocErrorReporter errorReporter) {
    delegate = errorReporter
  }

  static void printError(msg) {
    delegate.printError msg
  }

  static void printNotice(msg) {
    delegate.printNotice msg
  }

  static void printWarning(msg) {
    delegate.printWarning msg
  }
}



