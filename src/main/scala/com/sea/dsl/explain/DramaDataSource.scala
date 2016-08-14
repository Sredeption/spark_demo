package com.sea.dsl.explain

import org.apache.spark.sql.{DataFrame, Row}


class DramaDataSource(context: Context, dataFrame: DataFrame) {

  def where(leftColumn: List[Int], rightColumn: List[Int]): DramaList = {
    context.stepRDD(rdd => {
      val left = rdd.groupBy(r => leftColumn
        .map(i => r.get(i).toString)
        .reduce((a, b) => a + b)
      )
      val right = dataFrame.rdd.groupBy(r => rightColumn
        .map(i => r.get(i).toString)
        .reduce((a, b) => a + b)
      )
      val join = left.leftOuterJoin(right)
        .map(r => (r._2._1, r._2._2.getOrElse(Iterable.empty)))
      join.flatMap(r => {
        r._1.map(row => Row.fromSeq(row.toSeq ++ Seq(r._2)))
      })
    })
    null
  }


}
