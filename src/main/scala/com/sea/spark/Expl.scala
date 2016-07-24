package com.sea.spark

import java.io.InputStream

import groovy.lang.{Binding, GroovyShell}
import org.apache.spark.sql.Row
import org.codehaus.groovy.control.CompilerConfiguration


class Expl(source: InputStream) extends Serializable{
  val config = new CompilerConfiguration()
  val shell = new GroovyShell(getClass.getClassLoader, new Binding(), config)
  val script = shell.parse(source)

  def explain(row: Row) {
    script.getBinding.setVariable("row", row)
    script.run()
  }
}
