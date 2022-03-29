package edu.scala.functions

import java.util.{Calendar, Date};

object PartiallyAppliedFunction extends App {

  def log(time: Long, message: String) = {
    s"$time $message"
  }

  val sum = (a: Int, b: Int, c: Int) => a + b + c
  println(sum(10, 20, 30))

  val partialFunction = sum(10, 20, _)
  println(partialFunction(30))

  val logVal = log(System.nanoTime(),_)
  println(logVal("Hello World!"))
  println(logVal("How are you?"))
  println(logVal("Hope Everything is fine"))

}
