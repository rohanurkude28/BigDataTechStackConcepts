package edu.spark

import org.apache.spark.sql.SparkSession

object CommonUtility {
  val numList = List(1,2,3,4,5,6,7,8,9,10)
  val dateList = List(("2022-10-28",2),("2022-01-09",5),("2022-02-02",6),("2022-04-30",4),("2022-07-24",8))

  def getSparkSession()={
    val sparkSession = SparkSession.builder().appName("SparkSession").master("local").getOrCreate()
    sparkSession.sparkContext.hadoopConfiguration.set("spark.hadoop.validateOutputSpecs", "false")

    sparkSession
  }
}
