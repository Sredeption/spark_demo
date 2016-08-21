package com.sea.dsl.explain

import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.functions.udf

class DramaContext(source: String, dataFrame: DataFrame) extends Context(source) {
  val logger = Logger.getLogger(getClass)
  protected var fields: Array[String] = null
  protected var rdd: RDD[Row] = null
  this.fields = dataFrame.schema.fieldNames
  this.rdd = dataFrame.rdd
  val EXPLAIN = "__explain"
  val sqlContext = dataFrame.sqlContext

  val columns = dataFrame.columns
  val mapEmpty: Any => Map[Seq[String], Seq[(String, String)]] = r => Map.empty
  val toExp = udf(mapEmpty)
  val temp = dataFrame.withColumn(EXPLAIN, toExp(dataFrame(columns.head)))
  val context = sqlContext.createDataFrame(temp.map(r => {
    val m = r.getAs[Map[Seq[String], Seq[(String, String)]]]("__explain")
    val s = r.toSeq.toArray
    s(r.fieldIndex("__explain")) = m + (Seq("leftTable_p2", "rightTable_p5") -> Array.empty)
    Row.fromSeq(s.toSeq)
  }), temp.schema)
  context.printSchema()
  context.show()
  columns.foreach(c => {
    let(c, DramaObject(context(c)))
  })

  binding.setProperty("context", context)

  override def run(): Unit = {
    super.run()
    binding.getProperty("context").asInstanceOf[DataFrame].select("__explain").foreach(println)
  }

  override def stepRow(f: Row => Row): Unit = {
    rdd = rdd.map(f).cache()
  }

  override def stepRDD(f: RDD[Row] => RDD[Row]): Unit = {
    rdd = f(rdd).cache()
  }

  override protected def getFields: Array[String] = fields

}
