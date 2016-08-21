package com.sea.dsl.explain

import org.apache.spark.sql.Column

class DramaObject(val column: Column) {


  def plus(any: Any): DramaObject = {
    apply(any, DramaObject.ops("+"))
  }

  def gt(any: Any): DramaObject = {
    apply(any, DramaObject.ops(">"))
  }

  def eq(any: Any): DramaObject = {
    apply(any, DramaObject.ops("==="))
  }

  def apply(any: Any, f: (Column, Any) => Column) = {
    any match {
      case dramaObject: DramaObject => DramaObject(f(this.column, dramaObject.column))
      case _ => DramaObject(f(this.column, any))
    }
  }

  override def toString: String ={
    column.toString()
  }
}

object DramaObject {
  def apply(column: Column): DramaObject = new DramaObject(column)

  def ops: Map[String, (Column, Any) => Column] = Map(
    "+" -> ((a, b) => a + b),
    "===" -> ((a, b) => a === b),
    ">" -> ((a, b) => a > b)
  )
}