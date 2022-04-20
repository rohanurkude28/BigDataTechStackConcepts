package edu.scala.akka

import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.GraphDSL.Implicits.SourceArrow
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}

object AkkaGraphMerge extends App {
  implicit val actorSystem = ActorSystem()

  val source1 = Source(List("Akka", "is", "awesome"))
  val source2 = Source(List("learning", "Akka", "Streams"))
  val sink = Sink.foreach[(String, Int)](println)

  val graph = GraphDSL.create() { implicit builder =>
    val wordCounter = Flow[String]
      .fold[Map[String, Int]](Map()) { (map, record) =>
        map + (record -> (map.getOrElse(record, 0) + 1))
      }
      .flatMapConcat(m => Source(m.toList))

    val merge = builder.add(Merge[String](2))
    val counter = builder.add(wordCounter)

    source1 ~> merge ~> counter ~> sink
    source2 ~> merge
    ClosedShape
  }

  RunnableGraph.fromGraph(graph).run()

  //actorSystem.terminate()
}