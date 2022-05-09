package edu.scala.functions

object HigherOrderFunction {

  def main(args: Array[String]) {
    // Displays output by assigning
    // value and calling functions
    println(apply(format, 32))

    // Scala in built HOF
    val list = 1 :: 2 :: 3 :: Nil
    val multiplyBy2 = (x: Int) => x * 2
    println(list.map(x => multiplyBy2(x)))
  }

  // A higher order function
  def apply(x: Double => String, y: Double) = x(y)

  // Defining a function for
  // the format and using a
  // method toString()
  def format[R](z: R) = "{" + z.toString() + "}"
}
