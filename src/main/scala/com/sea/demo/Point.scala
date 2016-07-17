package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
class Point(xc: Int, yc: Int) extends Similarity {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
  }

  override def toString: String = "(" + x + ", " + y + ")"

  override def isSimilar(obj: Any): Boolean = {
    obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x
  }
}
