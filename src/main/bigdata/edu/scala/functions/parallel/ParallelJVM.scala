package edu.scala.functions.parallel

class ParallelJVM extends Thread {

  private val x = new AnyRef{}
  private var uidCount: Long = 0L

  override def run(): Unit = {
    println("Hello!")
    println("World!")
  }

  def getUniqueId()={
    uidCount += 1
    uidCount
  }

  def startThread()={
    val t = new Thread {
      override def run(): Unit = x.synchronized{
        val uids = for(i <- 0 until 10) yield getUniqueId()
        println(uids)
      }
    }

    t.start()
    t
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val t1 = new ParallelJVM
    val t2 = new ParallelJVM
    t1.start()
//    t1.join()
    t2.start()
//    t2.join()

    t1.startThread()
    t1.startThread()
  }
}
