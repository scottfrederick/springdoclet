package org.springdoclet

class Configuration {
  private static final String OPTION_DIRECTORY = '-d'
  private static final String OPTION_FILENAME = '-f'
  private static final String OPTION_STYLESHEET = '-stylesheet'
  private static final String OPTION_LINKPATH = '-linkpath'
  private static final String DEFAULT_DIRECTORY = '.'
  private static final String DEFAULT_FILENAME = './spring-summary.html'
  private static final String DEFAULT_STYLESHEET = './spring-summary.css'
  private static final String DEFAULT_LINKPATH = './'

  String[][] options

  def getOutputDirectory() {
    getOption(OPTION_DIRECTORY) ?: DEFAULT_DIRECTORY
  }

  def getOutputFileName() {
    getOption(OPTION_FILENAME) ?: DEFAULT_FILENAME
  }

  def getStyleSheet() {
    getOption(OPTION_STYLESHEET) ?: DEFAULT_STYLESHEET
  }

  boolean isDefaultStyleSheet() {
    return getStyleSheet() == DEFAULT_STYLESHEET
  }

  def getLinkPath() {
    getOption(OPTION_LINKPATH) ?: DEFAULT_LINKPATH
  }

  private String getOption(String optionName) {
    for (option in options) {
      if (option[0] == optionName) {
        return option[1]
      }
    }
    return null
  }

  int getOptionLength(option) {
    if (option.equals(OPTION_DIRECTORY)) {
      return 2
    } else if (option.equals(OPTION_FILENAME)) {
      return 2
    } else if (option.equals(OPTION_STYLESHEET)) {
      return 2
    } else if (option.equals(OPTION_LINKPATH)) {
      return 2
    }
    return 0
  }
}
