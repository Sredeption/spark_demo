package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
object Twice {
  def apply(x: Int): Int = x * 2

  def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
}

object TwiceTest extends App {
  val x = Twice(21)
  x match {
    case Twice(n) =>println(n)
  } // prints 21
}