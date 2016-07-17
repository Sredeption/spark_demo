package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
class IntPair(val x: Int, val y: Int)

object IntPair {

  import math.Ordering

  implicit def ipord: Ordering[IntPair] =
    Ordering.by(ip => (ip.x, ip.y))
}

