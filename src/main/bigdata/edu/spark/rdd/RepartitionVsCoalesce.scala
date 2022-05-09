package edu.spark.rdd

import edu.spark.CommonUtility

object RepartitionVsCoalesce {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val rdd = spark.sparkContext.parallelize(Range(0,20))
    println("From local[5] "+rdd.partitions.size)

    val rdd1 = spark.sparkContext.parallelize(Range(0,20), 6)
    println("parallelize : "+rdd1.partitions.size)

    rdd1.partitions.foreach(f=> f.toString)
    val rddFromFile = spark.sparkContext.textFile("src/main/resources/Essay.txt",9)

    println("TextFile : "+rddFromFile.partitions.size)

    rdd1.saveAsTextFile("/tmp/partition")
    val rdd2 = rdd1.repartition(4)
    println("Repartition size : "+rdd2.partitions.size)

    rdd2.saveAsTextFile("/tmp/re-partition")

    val rdd3 = rdd1.coalesce(4)
    println("Repartition size : "+rdd3.partitions.size)

    rdd3.saveAsTextFile("/tmp/coalesce")
  }
}
