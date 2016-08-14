package com.sea.dsl.explain

import org.apache.spark.sql.Row

class DramaDouble(context: Context) extends DramaObject(context) {
  def plus(dramaInt: DramaInt): DramaDouble = {
    val adder1 = this.getIndex
    val adder2 = dramaInt.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getDouble(adder1) + r.getInt(adder2)))
    )
    getContext.newDouble()
  }

  def plus(dramaDouble: DramaDouble): DramaDouble = {
    val adder1 = this.getIndex
    val adder2 = dramaDouble.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getDouble(adder1) + r.getDouble(adder2)))
    )
    getContext.newDouble()
  }

  def plus(dramaString: DramaString): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaString.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getDouble(adder1) + r.getString(adder2)))
    )
    getContext.newString()
  }
}
