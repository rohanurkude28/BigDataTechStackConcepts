package edu.scala.akka

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

sealed trait BankMessage
case class Deposit(deposit:Int) extends BankMessage
case class Withdraw(withdraw:Int) extends BankMessage
case object PrintBalance extends BankMessage

class ManageBankActor extends Actor{

  override def receive: Receive = {
    case Deposit(amount) => receive(balance + amount)
    case Withdraw(amount) => receive(balance - amount)
    case PrintBalance => println(s"Balance is $balance"); receive(balance)
  }
}

object ManageBankActorDemo extends App {
    val guardianActor = ActorSystem(name="ManageBankActor1")
    val systemActor = guardianActor.actorOf(Props(classOf[ManageBankActor]),"actorAccount1")

    println(systemActor)
    systemActor ! PrintBalance
    systemActor ! Deposit(200)
    systemActor ! Withdraw(50)
    systemActor ! PrintBalance

  systemActor.
}