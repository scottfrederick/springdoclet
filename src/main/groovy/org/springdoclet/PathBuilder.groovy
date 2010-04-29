package org.springdoclet

class PathBuilder {
  String linkUrl

  def PathBuilder(linkUrl) {
    this.linkUrl = linkUrl;
  }

  String buildFilePath(String className) {
    return linkUrl + className.replaceAll("\\.", '/') + '.html'
  }
}
