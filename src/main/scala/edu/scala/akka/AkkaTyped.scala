package edu.scala.akka;

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, PostStop, PreRestart, SupervisorStrategy};

object AkkaTyped extends App {
  object StringActor {
    def apply() : Behavior[String] = Behaviors.setup { context =>
      println("Before receiving messages")
      Behaviors.receiveMessage[String] {
        case "stop" =>
          Behaviors.stopped
        case "restart" =>
          throw new IllegalStateException("restart actor")
        case message =>
          println(s"received message $message")
          Behaviors.same
      }.receiveSignal {
        case(_, PostStop) =>
          println(s"stopping actor")
          Behaviors.stopped
        case (_, PreRestart) =>
          println("Restarting Actor")
          Behaviors.stopped
      }
    }
  }

  val stringBehaviour: Behavior[String] = Behaviors.supervise(StringActor()).onFailure[IllegalStateException](SupervisorStrategy.restart)
  val stringActor = ActorSystem(stringBehaviour,"StringActor")
  stringActor ! "Hello World"
  // stringActor ! "stop" //In Case we want stop after processing
  stringActor ! "restart"
}