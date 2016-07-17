package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
object RegExpTest extends App {
  def containsScala(x: String): Boolean = {
    val z: Seq[Char] = x
    z match {
      case Seq('s', 'c', 'a', 'l', 'a', rest@_*) =>
        println("rest is " + rest)
        true
      case Seq(_*)=>
        false
    }
  }

  println(containsScala("scalaasdf"))
}
