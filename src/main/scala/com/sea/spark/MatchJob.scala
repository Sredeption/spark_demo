package com.sea.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

import scala.collection.mutable.ArrayBuffer


class MatchJob(sqlContext: SQLContext, left: DataFrame, right: DataFrame, joinRules: JoinRules, validateRules: JoinRules) extends Serializable {
  val leftMap = left.rdd.groupBy(r => joinRules.getLeftKey(r))
  val rightMap = right.rdd.groupBy(r => joinRules.getRightKey(r))
  val table = leftMap.join(rightMap)
  val leftOnly = leftMap.keys.subtract(rightMap.keys).map(r => r -> 0).join(leftMap)
  val rightOnly = rightMap.keys.subtract(leftMap.keys).map(r => r -> 0).join(rightMap)

  val l = filterDuplicate(left.rdd, joinRules).filter(_._2._2 == 1).map(r => r._1 -> r._2._1)
  val ld = filterDuplicate(left.rdd, joinRules).filter(_._2._2 > 1)
  val r = filterDuplicate(right.rdd, joinRules).filter(_._2._2 == 1).map(r => r._1 -> r._2._1)
  val rd = filterDuplicate(right.rdd, joinRules).filter(_._2._2 > 1)
  val lo = sideOnly(l, r)
  val ro = sideOnly(r, l)

  val m = l.join(r).filter(r => validateRules.getLeftKey(r._2._1) == validateRules.getRightKey(r._2._2))
  val d = l.join(r).filter(r => validateRules.getLeftKey(r._2._1) != validateRules.getRightKey(r._2._2))

  println("left only:")
  lo.map(r => r._2).collect().foreach(println)
  println("left duplicate:")
  ld.collect().foreach(println)
  println("right only:")
  ro.map(r => r._2).collect().foreach(println)
  println("right duplicate:")
  rd.collect().foreach(println)
  println("match:")
  m.map(r => r._2).collect().foreach(println)
  println("diff:")
  d.map(r => r._2).collect().foreach(println)

  def matches(): DataFrame = {
    val fields = left.schema.map(s => s.copy(name = "leftTable_" + s.name)) ++ right.schema.map(s => s.copy(name = "rightTable_" + s.name))
    val structType = StructType(fields)
    sqlContext.createDataFrame(m.map(r => Row.fromSeq(r._2._1.toSeq ++ r._2._2.toSeq)), structType)
  }

  def diff(): DataFrame = {
    val fields = left.schema.map(s => s.copy(name = "leftTable_" + s.name)) ++ right.schema.map(s => s.copy(name = "rightTable_" + s.name))
    val structType = StructType(fields)
    sqlContext.createDataFrame(d.map(r => Row.fromSeq(r._2._1.toSeq ++ r._2._2.toSeq)), structType)
  }

  def filterDuplicate(rdd: RDD[Row], rules: JoinRules): RDD[(String, (Row, Int))] =
    rdd.map(r => rules.getLeftKey(r) -> (r -> 1))
      .reduceByKey((x, y) => x._1 -> (x._2 + y._2))

  def sideOnly(sbh: RDD[(String, Row)], sbt: RDD[(String, Row)]): RDD[(String, Row)] = {
    sbh.keys
      .subtract(sbt.keys)
      .map(r => r -> 0)
      .join(sbh)
      .map(r => r._1 -> r._2._2)
  }
}
