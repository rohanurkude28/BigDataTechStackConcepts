# Scala Concepts 

Underneath Code can be viewed with below commands
```scala
scalac -Xprint:all *.scala //For viewing how syntactic sugar is handled
scalap className //How code looks after compilation using bytecode
javap className //How equivalent Java code looks after compilation

```

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

