package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
object ComprehensionTest extends App {
  def foo(n: Int, v: Int) =
    for (i <- 0 until n;
         j <- i until n if i + j == v) yield
      Pair(i, j)

  foo(20, 32) foreach {
    case (i, j) =>
      println("(" + i + ", " + j + ")")
  }
}

object ComprehensionTest1 extends App {
  for (i <- Iterator.range(0, 20);
       j <- Iterator.range(i, 20) if i + j == 32)
    println("(" + i + ", " + j + ")")
}