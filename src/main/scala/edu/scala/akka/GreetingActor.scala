package edu.scala.akka

import akka.actor.{Actor, ActorSystem, Props}

case class Greeting(name:String)

class GreetingActor extends Actor{
  override def receive: Receive = {
    case Greeting(name) => {
      println(s"Hello $name")
    }
  }
}

object Test{
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("GreetingActor")
    val greeter = system.actorOf(Props[GreetingActor],name="greeter")
    greeter ! Greeting("Rohan")
    //system.terminate()
  }
}