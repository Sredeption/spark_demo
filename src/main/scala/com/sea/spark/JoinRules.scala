package com.sea.spark

import org.apache.spark.sql.Row

class JoinRules(val left: Seq[String], val right: Seq[String]) extends Serializable {
  def getLeftKey(r: Row): String = left.map(s => r.getAs(s).toString).reduce((a, b) => a ++ b)

  def getRightKey(r: Row): String = right.map(s => r.getAs(s).toString).reduce((a, b) => a ++ b)
}
