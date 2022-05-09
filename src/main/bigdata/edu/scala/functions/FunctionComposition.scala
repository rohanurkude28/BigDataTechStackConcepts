package edu.scala.functions

object FunctionComposition {

  def andThenFuncComposition() ={
    val add1 = (i: Int) => i + 1
    val double = (i: Int) => i * 2
    val addThenDouble = add1 andThen double
    println(addThenDouble(1))
  }

  def composeFuncComposition() ={
    val add1 = (i: Int) => i + 1
    val double = (i: Int) => i * 2
    val doubleThenAdd =  double compose add1
    println(doubleThenAdd(1))
  }


  def andThenFuncCompositionMultiParam() ={
    val f = (x: Int, y: Int) => (x + 1, y + 2)
    val g = (x: Int, y: Int) => x - y
    val h = f.tupled andThen g.tupled
    println(h(1,1))
  }

  def main(args: Array[String]): Unit = {
    andThenFuncComposition()
    composeFuncComposition()
    andThenFuncCompositionMultiParam()
  }
}
