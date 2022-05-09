package edu.scala.functions;

object ClosureFunction extends App {

  var number = 100

  def addNumber = (i: Int) => {
    i + number
  }
  println(addNumber(10))

  number = 200
  println(addNumber(110))
}
