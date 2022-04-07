# Scala Concepts 

Underneath Code can be viewed with below commands
```scala
scalac -Xprint:all *.scala //For viewing how syntactic sugar is handled
scalap className //How code looks after compilation using bytecode
javap className //How equivalent Java code looks after compilation

```

##Pure Object Orientation (Objects are everywhere)

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

##Functions as Objects


The function type A => B is just an abbreviation for the class `scala.Function[A,B]`, roughly defined as:

```scala
package scala
trait Function1[A, B] {
	def apply(x: A): B
}
```

###Expansion of Function Values

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

###Expansion of Function Calls

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

###Functions and Methods

Note that anything defined with a def, ie, a method, like
```scala 
def f(x: Int): Boolean = ...
``` 

is not itself a function value; but if the name of a method is used in a place where a function type is expected, it's converted automatically to the function value.

## Functions

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

## Polymorphism in Scala

### Parametric Polymorphism

We can easily recognize parametrically polymorphic functions in Scala by the presence of one or more type parameters delimited by square brackets in the method signature — they enable us to apply the same logic to different data types.

The Naive Solution : Below works well for integer arrays but is not reusable for other types:

```scala
def pairWiseReverseInt(xs: List[Int]): List[Int] = xs.grouped(2).flatMap(_.reverse).toList
```

DRY Solution : With parametric polymorphism, the logic remains the same for all the different types

```scala
def pairWiseReverse[A](xs:List[A]): List[A] = xs.grouped(2).flatMap(_.reverse).toList
```

Subtype Polymorphism : The key concept in subtype polymorphism is substitutability as defined in the Liskov substitution principle

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

Ad-Hoc Polymorphism : The compiler switches between different code implementations depending on the type of input a method receives.

When calling **.sorted** method on list scala knows via **Method Overloading** to call which function, but this doesn't apply to custom classes, 
where in we have to provide an implementation of Ordering type.

```scala
    val ord: Ordering[StudentId] = (x, y) => x.id.compareTo(y.id)
```

**Operator Overloading** Scala supports operator overloading, which means that the meaning of operators (such as * and +) may be defined for arbitrary types.

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


