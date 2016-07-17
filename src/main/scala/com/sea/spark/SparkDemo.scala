package com.sea.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * Created by Sea on 7/15/2016.
  */

case class DataDemo(p1: Long, p2: String, p3: Double)
object SparkDemo extends App {

  val conf = new SparkConf().setAppName("Spark Demo").setMaster("local[4]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  import sqlContext.implicits._

  val t2 = sqlContext.read.json("E:/M.S/Spark/data.json").select("p1", "p2", "p3").where("p1!=12")
  t2.show()
  t2.as[DataDemo]
}
