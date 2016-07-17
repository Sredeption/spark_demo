package com.sea.spark

import org.apache.spark.sql.{Column, DataFrame, SQLContext}


/**
  * Created by Sea on 7/17/2016.
  */
class MatchJob(sqlContext: SQLContext, left: DataFrame, right: DataFrame) {
  left.registerTempTable("Left")
  right.registerTempTable("Right")

  def joinOn(join: Seq[Column], validate:Seq[String]) {
    val leftGroup=left.groupBy(join: _*)
  }
}
