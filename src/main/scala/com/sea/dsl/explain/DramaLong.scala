package com.sea.dsl.explain

import org.apache.spark.sql.Row

class DramaLong(context: Context) extends DramaObject(context) {
  def plus(dramaInt: DramaInt): DramaLong = {
    val adder1 = this.getIndex
    val adder2 = dramaInt.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getLong(adder1) + r.getInt(adder2)))
    )
    getContext.newLong()
  }

  def plus(dramaLong: DramaLong): DramaLong = {
    val adder1 = this.getIndex
    val adder2 = dramaLong.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getLong(adder1) + r.getLong(adder2)))
    )
    getContext.newLong()
  }

  def plus(dramaDouble: DramaDouble): DramaDouble = {
    val adder1 = this.getIndex
    val adder2 = dramaDouble.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getLong(adder1) + r.getDouble(adder2)))
    )
    getContext.newDouble()
  }

  def plus(dramaString: DramaString): DramaString = {
    val adder1 = this.getIndex
    val adder2 = dramaString.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getLong(adder1) + r.getString(adder2)))
    )
    getContext.newString()
  }

  def equals(i: Int): DramaBoolean = {
    val idx1 = this.getIndex
    getContext.stepRow(r =>
      Row.fromSeq(r.toSeq ++ Seq(r.getLong(idx1) == i))
    )
    getContext.newBoolean
  }
}
