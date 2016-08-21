package com.sea.dsl.explain

import groovy.lang.Script
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.udf

abstract class DramaScript extends Script {


  private def context: DataFrame = {
    getBinding.getProperty("context").asInstanceOf[DataFrame]
  }

  def left(name: String): DramaObject = {
    getBinding.getVariable("leftTable_" + name).asInstanceOf[DramaObject]
  }

  def right(name: String): DramaObject = {
    getBinding.getVariable("rightTable_" + name).asInstanceOf[DramaObject]
  }

  def when(condition: DramaObject): Unit = {
    val brokenRule = Seq("leftTable_p2", "rightTable_p5")
    val explaination = ("y", "y")
    val explainFunc: (Boolean, Map[Seq[String], Seq[(String, String)]]) => Map[Seq[String], Seq[(String, String)]] =
      (b, map) => {
        if (b) {
          if (map.get(brokenRule).isEmpty) {
            map
          } else {
            map + (brokenRule -> (
              Seq(explaination) ++
                map(brokenRule)
              ))
          }
        }
        else
          map
      }
    val explain = udf(explainFunc)
    val temp = context.withColumn("__explain", explain(condition.column, context("__explain")))
    getBinding.setProperty("context", temp)
  }

}
