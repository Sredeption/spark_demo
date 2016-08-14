package com.sea.dsl.explain

import org.apache.spark.sql.Row

class DramaString(context: Context) extends DramaObject(context) {
  def plus(dramaInt: DramaInt): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaInt.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getString(adder1) + r.getInt(adder2)))
    )
    getContext.newString()
  }

  def plus(dramaDouble: DramaDouble): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaDouble.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getString(adder1) + r.getDouble(adder2)))
    )
    getContext.newString()
  }

  def plus(dramaString: DramaString): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaString.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getString(adder1) + r.getString(adder2)))
    )
    getContext.newString()
  }

  def toInt: DramaInt = {
    val idx1 = this.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getString(idx1).toInt))
    )
    getContext.newInt()
  }

  def toDouble: DramaDouble = {
    val idx1 = this.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getString(idx1).toDouble))
    )
    getContext.newDouble()
  }
}
