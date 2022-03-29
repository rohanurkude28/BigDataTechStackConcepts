package edu.scala.functions

object FunctionCurrying extends App {

  def add (x: Int, y: Int) : Int = x + y
  def add2 (x : Int) : Int => Int = (y: Int) => x + y
  def add3 (x : Int) (y: Int) : Int = x + y

  println(add2(2)(4))


  val add2to = add2(3)
  println(add2to(5))

  val add3Partialto = add3(3)_
  println(add3Partialto(11))


  val sum: (Int, Int) => Int = (x, y) => x + y
  println(sum(1,2))

  val curriedSum: Int => Int => Int = x => y => x + y
  println(curriedSum(1)(2))
}
