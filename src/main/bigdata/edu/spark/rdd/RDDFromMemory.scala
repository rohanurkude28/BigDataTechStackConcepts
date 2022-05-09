package edu.spark.rdd

import edu.spark.CommonUtility

object RDDFromMemory {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    val numList = CommonUtility.numList

    val rdd = spark.sparkContext.parallelize(numList)
    rdd.foreach(println)
  }
}
