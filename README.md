# Scala Concepts 

- [Scala Style Guide : ](ScalaStyleGuide.md)
- [Scala Best Practices : ](ScalaBestPractices.md)
- [Parallel Programming : ](ParallelProgramming.md)

Underneath Code can be viewed with below commands
```scala
scalac -Xprint:all *.scala //For viewing how syntactic sugar is handled
scalap className //How code looks after compilation using bytecode
javap className //How equivalent Java code looks after compilation

```

## <a name='TOC'>Table of Contents</a>

1. [Objects are Everwhere](##PureObjectOrientation)
2. [Functions](##Functions)
3. [Polymorphism](##Polymorphism)
4. [Variances in Scala](##Variances)
5. [Pattern Matching](##PatternMatching)
6. [Pattern Matching](##PatternMatching)
   
## <a name='PureObjectOrientation'>Pure Object Orientation (Objects are everywhere)</a>

In Java, there is a difference between an expression and a statement.

```java
int value = 10;
        
boolean result;
        
if(value > 20) {
  result = true;
}
else {
  result = false;
}
```

In Scala, almost everything(there are some exceptions) evaluates to an expression. Everything is a value in Scala.
It's just mostly syntactic sugar.

```scala
val number = 20

  val s = {
    if(number > 10){
      println("Number is greater")
    }

    else {
      println("Number is lesser")
    }
    "testing"
  }

  println(s)
```

Data types as Objects : There are no native data types in scala and all of the data types have a class of their own
Operations on types : All operations that we do in primitive java types such as +,-,* etc ., are implemented as methods.

## Functions as Objects


The function type A => B is just an abbreviation for the class `scala.Function[A,B]`, roughly defined as:

```scala
package scala
trait Function1[A, B] {
	def apply(x: A): B
}
```

### Expansion of Function Values

An anonymous function such as `(x: Int) => x * x` is expanded to

```scala
{ class AnonFun extends Function1[Int, Int] {
		def apply(x: Int) = x * x
	}
	new AnonFun
}
```

Or, shorter, using anonymous class syntax, like in Java:

```scala
new Function1[Int, Int] {
	def apply(x: Int) = x * x
}
```

### Expansion of Function Calls

A function call, such as f(a,b), where f is a value of some class type, is expanded to f.apply(a,b)
So the OO-translation of

```scala
val f = (x: Int) => x * x
f(7)
```

would be

```scala
val f = new Function1[Int, Int] {
	def apply(x: Int) = x * x
}
f.apply(7)
```

### Functions and Methods

Note that anything defined with a def, ie, a method, like
```scala 
def f(x: Int): Boolean = ...
``` 

is not itself a function value; but if the name of a method is used in a place where a function type is expected, it's converted automatically to the function value.

## <a name='Functions'>Functions</a>

Functions are first-class citizens

- We can use functions as values or like normal variables; we can replace a variable or value with a function 
- We can assign a function literal to a variable 
- We can pass one or more functions as another function's parameters 
- We can return a function from another function

### Higher Order Functions in Scala

A function is called Higher Order Function if it contains other functions as a parameter or returns a function as an output

eg: map is a HOF in scala which take function as an input


```scala
val list = 1 :: 2 :: 3 :: Nil
val multiplyBy2 = (x: Int) => x * 2
list.map(x => multiplyBy2(x))
```

### Function Currying

Currying is converting a single function of n arguments into n functions with a single argument each
```scala
val curriedSum: Int => Int => Int = x => y => x + y
println(curriedSum(1)(2))
```

### Partially Applied Function

A partially applied function is a function where some of its arguments have already been filled in.
```scala
def log(time: Long, message: String) = {
  s"$time $message"
}

val logVal = log(System.nanoTime(),_)
```

### Partially Function

- A partial function is a function that does not provide an answer for every possible input value it can be given.
- PartialFunction is a trait

```scala
trait PartialFunction[-A, +B] extends (A) => B
```

### Anonymous Function

- Anonymous function in Scala — also known as a function literal using which you can pass it into a method that takes a function, or to assign it to a variable.

```scala
val list = 1 :: 2 :: 3 :: Nil
val evens = list.filter((i: Int) => i % 2 == 0)
```

`(i: Int) => i % 2 == 0` - Anonymous Function

### Closure

- A closure is a function which uses one or more variables declared outside this function
- Takes most recent value declare

```scala
var number = 100

val addNumber = (i: Int) => {
  i + number
}
println(addNumber(10))
```


### Function Composition

- Given two functions, f: X -> Y and g: Y -> Z, we can define their composition as a function h = g ∘ f : X -> Z, where h(x) = g(f(x))
- Function composition is always associative: f ∘ (g ∘ h) = (f ∘ g) ∘ h.
- The trait Function1[T1, R] defines methods to compose functions.
- There are two ways to compose such functions, according to Function1: compose and andThen.
- compose is g(f(x)) and andThen is f(g(x)), ordering of apply is different

```scala
    val add1 = (i: Int) => i + 1
    val double = (i: Int) => i * 2
    val addComposeDouble =  double compose add1
    println(addComposeDouble(1))

    val doubleAndThenAdd = add1 andThen double
    println(doubleAndThenAdd(1))
```
- .tupled is used in case of multiple parameters (Present inside Function trait)

```scala
def tupled: Tuple2[T1, T2] => R = {
  case Tuple2(x1, x2) => apply(x1, x2)
}
```

## <a name='Polymorphism'>Polymorphism in Scala</a>

### Parametric Polymorphism

We can easily recognize parametrically polymorphic functions in Scala by the presence of one or more type parameters delimited by square brackets in the method signature — they enable us to apply the same logic to different data types.

**The Naive Solution** : Below works well for integer arrays but is not reusable for other types:

```scala
def pairWiseReverseInt(xs: List[Int]): List[Int] = xs.grouped(2).flatMap(_.reverse).toList
```

**DRY Solution** : With parametric polymorphism, the logic remains the same for all the different types

```scala
def pairWiseReverse[A](xs:List[A]): List[A] = xs.grouped(2).flatMap(_.reverse).toList
```

**Subtype Polymorphism** : The key concept in subtype polymorphism is substitutability as defined in the Liskov substitution principle

```scala
trait Shape {
    def getArea: Double
}
case class Square(side: Double) extends Shape {
    override def getArea: Double = side * side
}
case class Circle(radius: Double) extends Shape {
    override def getArea: Double = Math.PI * radius * radius
}

def printArea[T <: Shape](shape: T): Double = (math.floor(shape.getArea) * 100)/100
```

**Ad-Hoc Polymorphism** : The compiler switches between different code implementations depending on the type of input a method receives.

**Method Overloading** : When calling **.sorted** method on list scala knows via Method Overloading to call which function, but this doesn't apply to custom classes, 
where in we have to provide an implementation of Ordering type.

```scala
    val ord: Ordering[StudentId] = (x, y) => x.id.compareTo(y.id)
```

**Operator Overloading** : Scala supports operator overloading, which means that the meaning of operators (such as * and +) may be defined for arbitrary types.

```scala
class Complex(val real : Double, val imag : Double) {
  def +(other : Complex) = new Complex(
    real + other.real,
    imag + other.imag)


  def (other : Complex) = new Complex(
    realother.real - imagother.imag,
    imagother.real + real*other.imag)



  def magnitude() : Double = Math.sqrt(realreal + imagimag)
}

var C = new Complex(x, y)
var Z = new Complex(0.0, 0.0)

var count = 0
while (count < THRESHOLD && Z.magnitude() < 2.0) {
  Z = Z*Z + C
  count += 1
}
```

This code determines whether a complex number C


## <a name='Variances'>Variances in Scala</a>

Variance is the interconnection of subtyping relationship between complex types and their component types.
There are three types of variance: covariance, contravariance, and invariance.

### Covariance F[+T]

We say that a type constructor F[_] is covariant if B is a subtype of type A and F[B] is a subtype of type F[A].
eg: If S is subtype of T then List[S] is a subtype of List[T].

Covariance is type-safe because it reflects the standard behavior of subtyping.

```scala
class Shape[+T](polygon: T)
sealed trait Polygon
case object Parallelogram extends Polygon

val shape: Shape[Polygon] = new Shape[Parallelogram](List(new Parallelogram))
```

In Scala : List[T], Option[T], and Try[T].

### Contravariance F[-T]

We say that a type constructor F[_] is contravariant if B is a subtype of type A and F[A] is a subtype of type F[B]. This relation is precisely the contrary of the covariance relation.
eg: If S is subtype of T then List[T] is a subtype of List[S].


```scala
trait Vet[-T] { // we can also insert an optional -T <: Animal here if we wanted to impose a type constraint
  def heal(animal: T): Boolean
}

val myDog = new Dog("Buddy")
val myVet: Vet[Dog] = new Vet[Animal] { ... }
myVet.heal(myDog)
```

We're declaring a Vet[Dog], and instead we have a Vet[Animal], with the meaning that the vet can heal any animal; therefore, it can work on my dog as well. The code will compile, our buddy will live, and we would be happy.

### Invariance F[_]

We say that a type constructor F[_] is invariant if any subtype relationship between types A and B is not preserved in any order between types F[A] and F[B].

eg: If S is subtype of T then List[S] and List[T] don’t have inheritance relationship or sub-typing. That means both are unrelated.

```scala
class Shape[T](polygon: T)
case object Parallelogram
case object Rectangle extends Parallelogram

val suite: Shape[Parallelogram] = new Shape[Parallelogram](List(new Parallelogram))
```

## <a name='PatternMatching'>Pattern Matching</a>

### Patterns in Match Expression

**Case Classes** 

```scala
def caseClassesPatternMatching(animal: Animal): String = {
  animal match {
    case Mammal(name, fromSea) => s"I'm a $name, a kind of mammal. Am I from the sea? $fromSea"
    case Bird(name) => s"I'm a $name, a kind of bird"
    case _ => "I'm an unknown animal"
  }
}
```

**Constants**

```scala
def constantsPatternMatching(constant: Any): String = {
  constant match {
    case 0 => "I'm equal to zero"
    case 4.5d => "I'm a double"
    case false => "I'm the contrary of true"
    case _ => s"I'm unknown and equal to $constant"
  }
}
```

**Sequences**

```scala
def sequencesPatternMatching(sequence: Any): String = {
  sequence match {
    case List(singleElement) => s"I'm a list with one element: $singleElement"
    case List(_, _*) => s"I'm a list with one or multiple elements: sequence"
    case Vector(1, 2, _*) => s"I'm a vector: $sequence"
    case _ => s"I'm an unrecognized sequence. My value: $sequence"
  }
}
```

**Tuples**

```scala
def tuplesPatternMatching(tuple: Any): String = {
  tuple match {
    case (first, second) => s"I'm a tuple with two elements: $first & $second"
    case (first, second, third) => s"I'm a tuple with three elements: $first & $second & $third"
    case _ => s"Unrecognized pattern. My value: $tuple"
  }
}
```

**Typed Patterns**

```scala
def typedPatternMatching(any: Any): String = {
  any match {
    case string: String => s"I'm a string. My value: $string"
    case integer: Int => s"I'm an integer. My value: $integer"
    case _ => s"I'm from an unknown type. My value: $any"
  }
}
```

**Regex Patterns**

```scala
def regexPatterns(toMatch: String): String = {
  val numeric = """([0-9]+)""".r
  val alphabetic = """([a-zA-Z]+)""".r
  val alphanumeric = """([a-zA-Z0-9]+)""".r

  toMatch match {
    case numeric(value) => s"I'm a numeric with value $value"
    case alphabetic(value) => s"I'm an alphabetic with value $value"
    case alphanumeric(value) => s"I'm an alphanumeric with value $value"
    case _ => s"I contain other characters than alphanumerics. My value $toMatch"
  }
}
```

**Options: Some<T> and None**

```scala
def optionsPatternMatching(option: Option[String]): String = {
  option match {
    case Some(value) => s"I'm not an empty option. Value $value"
    case None => "I'm an empty option"
  }
}
```

**Variable Binding**

```scala
def matchType(x: Any): String = x match {
  //case x: List(1, _*) => s"$x"          // doesn't compile
  case x @ List(1, _*) => s"$x"           // works; prints the list
  //case Some(_) => "got a Some"          // works, but can't access the Some
  //case Some(x) => s"$x"                 // works, returns "foo"
  case x @ Some(_) => s"$x"               // works, returns "Some(foo)"
  case p @ Person(first, "Doe") => s"$p"  // works, returns "Person(John,Doe)"
}

println(matchType(List(1,2,3)))             // prints "List(1, 2, 3)"
println(matchType(Some("foo")))             // prints "Some(foo)"
println(matchType(Person("John", "Doe")))   // prints "Person(John,Doe)"
```

**Pattern Guards**

```scala
def patternGuards(toMatch: Any, maxLength: Int): String = {
  toMatch match {
    case list: List[Any] if (list.size <= maxLength) => "List is of acceptable size"
    case list: List[Any] => "List has not an acceptable size"
    case string: String if (string.length <= maxLength) => "String is of acceptable size"
    case string: String => "String has not an acceptable size"
    case _ => "Input is neither a List nor a String"
  }
}
```

**Sealed Classes** A sealed class is a superclass that is aware of every single class extending it

```scala
sealed trait Shape
case class Square(height: Int, width: Int) extends Shape
case class Circle(radius: Int) extends Shape
case object Point extends Shape


def matchShape(shape: Shape): String = shape match {
    case Square(height, width) => "It's a square"
    case Circle(radius)        => "It's a circle"
    //no case for Point because it would cause a compiler warning. Scala will check at compile-time that all cases are 'exhaustively matched'
}
```

**Extractors** Extractor objects are objects containing a method called unapply. This method is executed when matching against a pattern is successful.


```scala
object Person {
  def apply(fullName: String) = fullName

  def unapply(fullName: String): Option[String] = {
    if (!fullName.isEmpty)
      Some(fullName.replaceAll("(?<=\\w)(\\w+)", "."))
    else
      None
  }
}

def extractors(person: Any): String = {
  person match {
    case Person(initials) => s"My initials are $initials"
    case _ => "Could not extract initials"
  }
}
```


**Catch Blocks**

```scala
def catchBlocksPatternMatching(exception: Exception): String = {
  try {
    throw exception
  } catch {
    case ex: IllegalArgumentException => "It's an IllegalArgumentException"
    case ex: RuntimeException => "It's a RuntimeException"
    case _ => "It's an unknown kind of exception"
  }
}
```


**Closures**

```scala
def closuresPatternMatching(list: List[Any]): List[Any] = {
  list.collect { case i: Int if (i < 10) => i }
}
```