package edu.scala.functions

object AnonymousFunction extends App {

  val list = 1 :: 2 :: 3 :: 4 :: 6 :: 9 :: Nil
  val evens = list.filter((i: Int) => i % 2 == 0)

  println(evens)
}
