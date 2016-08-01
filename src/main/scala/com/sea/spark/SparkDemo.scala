package com.sea.spark

import com.sea.dsl.Explainer
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * Created by Sea on 7/15/2016.
  */

object SparkDemo extends App {

  val conf = new SparkConf().setAppName("Spark Demo").setMaster("local[4]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  val t1 = sqlContext.read.json(getClass.getClassLoader.getResource("data1.json").getFile)
  val t2 = sqlContext.read.json(getClass.getClassLoader.getResource("data2.json").getFile)
  val job = new MatchJob(sqlContext, t1, t2, new JoinRules(Seq[String]("p1"), Seq[String]("p1")), new JoinRules(Seq[String]("p2"), Seq[String]("p5")))
  val t = job.matches()
  t.show()
  val d = job.diff()

  d.schema.fields
  def explainer = new Explainer(getClass.getClassLoader.getResourceAsStream("exp2.groovy"))

  d.show()
  d.collect().foreach(explainer.explain)
}

