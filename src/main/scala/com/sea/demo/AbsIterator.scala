package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
abstract class AbsIterator {
  type T

  def hasNext: Boolean

  def next: T
}

trait RichIterator extends AbsIterator {
  def foreach(f: T => Unit) {
    while (hasNext) f(next)
  }
}

class StringIterator(s: String) extends AbsIterator {
  type T = Char
  private var i = 0

  override def hasNext: Boolean = i < s.length

  override def next: Char = {
    val ch = s charAt i
    i += 1
    ch
  }
}

object StringIteratorTest {
  def main(args: Array[String]) {
    class Iter extends StringIterator("elegant") with RichIterator
    val iter = new Iter
    iter foreach println
  }
}