package org.springdoclet

import com.sun.javadoc.Doclet
import com.sun.javadoc.RootDoc
import com.sun.javadoc.ClassDoc

class SpringDoclet extends Doclet {
  ClassProcessor classProcessor

  public static boolean start(RootDoc root) {
    try {
      SpringDoclet doclet = initDoclet()
      doclet.process(root)
    } catch (Exception e) {
      e.printStackTrace()
      return false;
    }

    return true
  }

  private static SpringDoclet initDoclet() {
    ClassProcessor classProcessor = new ClassProcessor()
    classProcessor.collectors = [
            new ComponentCollector(),
            new RequestMappingCollector()
    ]

    def doclet = new SpringDoclet()
    doclet.classProcessor = classProcessor
    return doclet
  }

  void process(RootDoc root) {
    ClassDoc[] classes = root.classes()
    classes.each { ClassDoc cls ->
      classProcessor.process(cls)
    }

    println classProcessor.toString()
  }
}
