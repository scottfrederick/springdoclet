package org.springdoclet

import java.text.BreakIterator

class TextUtils {
  public static getFirstSentence(text) {
    BreakIterator boundary = BreakIterator.getSentenceInstance();
    boundary.setText(text);
    int start = boundary.first();
    int end = boundary.next();
    if (start > -1 && end > -1) {
      text = text.substring(start, end).trim();
      text = text.replaceAll('\n', "");
    }
    return text;
  }
}
