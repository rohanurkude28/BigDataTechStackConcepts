package edu.scala.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import edu.scala.akka.ManageBankActorDemo.behaviour

sealed trait BankMessage
case class Deposit(deposit:Int) extends BankMessage
case class Withdraw(withdraw:Int) extends BankMessage
case object PrintBalance extends BankMessage

class ManageBankActorDemo extends Actor {
  override def receive(balance:Int) = {
    case Deposit(amount) => receive(balance + amount)
    case Withdraw(amount) => receive(balance - amount)
    case PrintBalance => println(s"Balance is $balance"); receive(balance)
  }

  override def receive: Receive = ???
}

object ManageBankActorDemo extends App {

    val guardianActor = ActorSystem(name="ManageBankActor1")
    val systemActor: ActorRef = guardianActor.actorOf(Props[ManageBankActorDemo],"actorAccount1")

    println(systemActor)
    systemActor ! PrintBalance
    systemActor ! Deposit(200)
    systemActor ! Withdraw(50)
    systemActor ! PrintBalance
}


class HelloActor extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case _       => println("huh?")
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
}