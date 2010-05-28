package org.springdoclet.writers

import org.springdoclet.Configuration

class StylesheetWriter {

  def writeStylesheet(Configuration config) {
    def file = new File(config.outputDirectory, config.styleSheet)
    file << STYLESHEET_CONTENT
  }

  private static final STYLESHEET_CONTENT = """
body {
  background: #fff;
  color: #333;
  font: 12px verdana, arial, helvetica, sans-serif;
  margin: 20px;
}

a {
  color: #003300;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

h1 {
  color: #007c00;
  font-weight: normal;
  font-size: 3em;
  text-align: center;
}

h2 {
  color: #83aa59;
  font-weight: normal;
  font-size: 2em;
}

h3 {
  color: #83aa59;
  font-weight: normal;
  font-size: 1.5em;
}

table {
  border: none;
  font-size: 1em;
}

th {
  color: #fff;
  background-color: #007c00;
}

td {
  border: none;
  border-bottom: 1px solid #ddd;
  padding: .5em;
}
"""
}
