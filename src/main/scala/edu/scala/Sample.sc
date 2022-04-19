def multiplyAndCheckEven(a: Int, b: Int, checkEven: (Int => Boolean)) = {
  checkEven(a * b)
}
multiplyAndCheckEven(1, 2, (x: Int) => (x % 2) == 0)


def partiallyAppliedSum(a: Int, b: Int) = {
  a + b
}
val add2To = partiallyAppliedSum(2,_)
add2To(5)

val addTwoNumbers = (a : Int,b : Int) => a+b
val doubleIt = (a : Int) => a*2
(addTwoNumbers.tupled andThen doubleIt)(1,2)

val createTuple = (a : Int,b : Int) => (a,b)
val mutliplyBoth = (a : Int,b : Int) => a*b
(createTuple.tupled andThen mutliplyBoth.tupled)(1,2)


type Validator = PartialFunction[Int,Option[String]]
val isEven = new Validator{
  override def isDefinedAt(value: Int): Boolean = (value%2)==0

  override def apply(value: Int): Option[String] = Some(s"Value $value is Even")
}
val isOdd = new Validator{
  override def isDefinedAt(value: Int): Boolean = (value%2)!=0

  override def apply(value: Int): Option[String] = Some(s"Value $value is Odd")
}

val evaluatedExpr = isEven.orElse(isOdd)

evaluatedExpr(3)


def curriedSum(a:Int)(b:Int)={a+b}
val sum = curriedSum(1)(_)

sum(5)



val optionalList : List[Option[String]] = List(Some("Rohan"),Some("Ali"),Some("Prabhjot"),Some("Shilpa"),Option.empty[String])

optionalList.flatten

val optionalList2 : List[Option[String]] = List(Some("Satheesh"),Some("Kartik"),Some("Arun"))
val list : List[List[Option[String]]] = List(optionalList,optionalList2)

list.flatMap(_.flatten)