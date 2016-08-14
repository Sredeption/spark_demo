package com.sea.dsl.explain

import org.apache.spark.sql.Row

class DramaInt(context: Context) extends DramaObject(context) {
  def plus(dramaInt: DramaInt): DramaInt = {
    val adder1 = this.getIndex
    val adder2 = dramaInt.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getInt(adder1) + r.getInt(adder2)))
    )
    getContext.newInt()
  }

  def plus(dramaDouble: DramaDouble): DramaDouble = {
    val adder1 = this.getIndex
    val adder2 = dramaDouble.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getInt(adder1) + r.getDouble(adder2)))
    )
    getContext.newDouble()
  }

  def plus(dramaString: DramaString): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaString.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getInt(adder1) + r.getString(adder2)))
    )
    getContext.newString()
  }
}
