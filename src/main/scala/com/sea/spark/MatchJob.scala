package com.sea.spark

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

import scala.collection.mutable.ArrayBuffer


class MatchJob(sqlContext: SQLContext, left: DataFrame, right: DataFrame, joinRules: JoinRules, validateRules: JoinRules) extends Serializable {
  val leftMap = left.rdd.groupBy(r => joinRules.getLeftKey(r))
  val rightMap = right.rdd.groupBy(r => joinRules.getRightKey(r))
  val table = leftMap.join(rightMap)

  def matches(): DataFrame = {
    val res = table.flatMap(m => {
      val leftRows = m._2._1.groupBy(r => validateRules.getLeftKey(r))
      val rightRows = m._2._2.groupBy(r => validateRules.getRightKey(r))
      leftRows.flatMap(k => k._2.flatMap(lr => rightRows.getOrElse(k._1, new ArrayBuffer()).map(rr => lr.toSeq ++ rr.toSeq)))
    }).map[Row](s => Row.fromSeq(s))
    val fields = left.schema.map(s => s.copy(name = "left." + s.name)) ++ right.schema.map(s => s.copy(name = "right." + s.name))
    val structType = StructType(fields)
    sqlContext.createDataFrame(res, structType)
  }

  def diff(): DataFrame = {
    val res = table.flatMap(m => {
      val leftRows = m._2._1.groupBy(r => validateRules.getLeftKey(r))
      val rightRows = m._2._2.groupBy(r => validateRules.getRightKey(r))
      val leftUnMatch = (leftRows.keySet -- rightRows.keySet).toList
      val rightUnMatch = (rightRows.keySet -- leftRows.keySet).toList
      val leftRemain = (leftRows.keySet -- leftUnMatch).toList
      val lm = leftUnMatch.flatMap(k => leftRows.get(k).get.flatMap(lr => {
        rightRows.values.reduce((a, b) => a ++ b).map(rr => lr.toSeq ++ rr.toSeq)
      }))
      val rm = rightUnMatch.flatMap(k => rightRows.get(k).get.flatMap(rr => {
        leftRemain.map(k => leftRows.get(k).get).reduce((a, b) => a ++ b).map(lr => lr.toSeq ++ rr.toSeq)
      }))
      lm ++ rm
    }).map[Row](s => Row.fromSeq(s))
    val fields = left.schema.map(s => s.copy(name = "left." + s.name)) ++ right.schema.map(s => s.copy(name = "right." + s.name))
    val structType = StructType(fields)
    sqlContext.createDataFrame(res, structType)
  }

}
