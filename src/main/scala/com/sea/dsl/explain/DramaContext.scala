package com.sea.dsl.explain

import java.util

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.{DataFrame, Row}

class DramaContext(source: String, dataFrame: DataFrame) extends Context(source) {
  val logger = Logger.getLogger(getClass)
  protected var fields: Array[String] = null
  protected var rdd: RDD[Row] = null
  this.fields = dataFrame.schema.fieldNames
  this.rdd = dataFrame.rdd

  dataFrame.schema.foreach(structField => {
    if (structField.dataType == DataTypes.IntegerType)
      let(structField.name, newInt)
    else if (structField.dataType == DataTypes.LongType)
      let(structField.name, newLong)
    else if (structField.dataType == DataTypes.DoubleType)
      let(structField.name, newDouble)
    else if (structField.dataType == DataTypes.StringType)
      let(structField.name, newString)
    else if (structField.dataType == DataTypes.BooleanType)
      let(structField.name, newBoolean)
  })

  override def stepRow(f: Row => Row): Unit = {
    rdd = rdd.map(f).cache()
  }

  override def stepRDD(f: RDD[Row] => RDD[Row]): Unit = {
    rdd = f(rdd).cache()
  }

  override protected def getFields: Array[String] = fields

  override def leaveScope(): util.List[DramaObject] = {
    stepRow(r => Row.fromSeq(Range(0, fp).map(i => r.get(i))))
    super.leaveScope
  }

  override def explain(dramaBoolean: DramaBoolean, content: String): Unit = {
    val idx = dramaBoolean.getIndex
    rdd.foreach(r => {
      if (r.getBoolean(idx))
        println(s"$r:$content")
      r
    })
  }
}
