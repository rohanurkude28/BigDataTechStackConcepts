package edu.spark.rdd

import edu.spark.CommonUtility

object AverageFriendByAgeCalculator {
  def parseLine(line:String)={
    val fields = line.split(",")
    (fields(1).toInt,fields(2).toInt)
  }

  def main(args: Array[String]): Unit = {
    val spark =  CommonUtility.getSparkSession()
    val lines = spark.sparkContext.textFile("src/main/resources/FriendsData.txt")
    val rdd = lines.map(parseLine).map(x => (x._1,(x._2,1))).reduceByKey((x,y) =>(x._1+y._1,x._2+y._2))
    val averageFriendsByAge = rdd.mapValues(x => x._1/x._2).collect()
    averageFriendsByAge.foreach(x => println(x))
  }

}
