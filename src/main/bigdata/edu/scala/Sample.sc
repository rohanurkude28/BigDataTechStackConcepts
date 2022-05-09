import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Try

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
evaluatedExpr(4)

def curriedSum(a:Int)(b:Int)={a+b}
val sum = curriedSum(1)(_)

sum(5)

val optionalList : List[Option[String]] = List(Some("Rohan"),Some("Ali"),Some("Prabhjot"),Some("Shilpa"),Option.empty[String])

optionalList.flatten

val optionalList2 : List[Option[String]] = List(Some("Satheesh"),Some("Kartik"),Some("Arun"))
val list : List[List[Option[String]]] = List(optionalList,optionalList2)

list.flatMap(_.flatten)


val aSuccessfulComputation = Try(22) // Success(22)
val aModifiedComputation = aSuccessfulComputation.map(_ + 1) // Success(23)
val badMath = (x: Int) => Try(x / 0) // Int => Try[Int]

val aFailedComputation = aSuccessfulComputation.flatMap(badMath) // Failure(ArithmeticException)
val aRecoveredComputation = aFailedComputation.recover {
  case _: ArithmeticException => -1
}  // Success(-1)

val aChainedComputation = for {
  x <- aSuccessfulComputation
  y <- aRecoveredComputation
} yield x + y // Success(21)

implicit val globalExecutionContext: ExecutionContext = ExecutionContext.global

def a = Future { Thread.sleep(2000); 100 }
def b = Future { Thread.sleep(2000); throw new NullPointerException }

Await.ready(a, Duration.Inf) // Future(Success(100))
Await.ready(b, Duration.Inf) // Future(Failure(java.lang.NullPointerException))

Await.result(a, Duration.Inf) // 100
//Await.result(b, Duration.Inf) // crash with java.lang.NullPointerException

implicit def intToStr(num: Int): String = s"The value is $num"
println(42.toUpperCase()) // evaluates to "THE VALUE IS 42"