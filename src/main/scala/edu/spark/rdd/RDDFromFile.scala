package edu.spark.rdd

import edu.spark.CommonUtility

object RDDFromFile {

  def main(args: Array[String]): Unit = {
    implicit val spark = CommonUtility.getSparkSession()
    val path = "src/main/resources/multifiles/*"

    val rdd = spark.sparkContext.textFile(path)
    rdd.collect.foreach(println)

    val rddWhole = spark.sparkContext.wholeTextFiles(path)
    rddWhole.foreach(f=>{
      println(f._1+"=>"+f._2)
    })

    val rdd3 = spark.sparkContext.textFile("src/main/resources/multifiles/text01.txt,src/main/resources/multifiles/text02.txt")
    rdd3.foreach(println)

    val rdd4 = spark.sparkContext.textFile("src/main/resources/multifiles/text*.txt")
    rdd4.foreach(println)
  }

}
