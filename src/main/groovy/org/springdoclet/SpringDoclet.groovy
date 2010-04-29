package org.springdoclet

import com.sun.javadoc.Doclet
import com.sun.javadoc.RootDoc
import org.springdoclet.writers.StylesheetWriter
import org.springdoclet.writers.HtmlWriter
import org.springdoclet.collectors.RequestMappingCollector
import org.springdoclet.collectors.ComponentCollector

class SpringDoclet extends Doclet {
  private static Configuration config = new Configuration()

  public static boolean start(RootDoc root) {
    ErrorReporter.setErrorReporter(root)
    config.options = root.options()

    def collectors = getCollectors()

    new ClassProcessor().process root.classes(), collectors

    new HtmlWriter().writeOutput collectors, config

    if (config.isDefaultStyleSheet()) {
      new StylesheetWriter().writeStylesheet config
    }

    return true
  }

  private static getCollectors() {
    return [ new ComponentCollector(), new RequestMappingCollector() ]
  }

  public static int optionLength(String option) {
    return config.getOptionLength(option)
  }
}
