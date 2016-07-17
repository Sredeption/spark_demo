package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
abstract class Term

case class V(name: String) extends Term

case class F(arg: String, body: Term) extends Term

case class A(f: Term, v: Term) extends Term


object TermTest extends App {
  def printTerm(term: Term): Unit = {
    term match {
      case V(n) =>
        print(n)
      case F(x, b) =>
        print("^" + x + ".")
        printTerm(b)
      case A(f, v) =>
        print("(")
        printTerm(f)
        print(" ")
        printTerm(v)
        print(")")
    }
  }

  def isIdentityFun(term: Term): Boolean = term match {
    case F(x, V(y)) if x == y => true
    case _ => false
  }

  val x1 = V("x")
  val x2 = V("x")
  val y1 = V("y")
  println(x1.name)
  println("" + x1 + " == " + x2 + " => " + (x1 == x2))

  val id = F("x", V("x"))
  val t = F("x", F("y", A(V("x"), V("y"))))
  printTerm(t)
  println()
  println(isIdentityFun(id))
  println(isIdentityFun(t))
}
