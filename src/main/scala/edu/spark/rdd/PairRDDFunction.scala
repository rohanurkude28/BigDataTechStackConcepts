package edu.spark.rdd

import edu.spark.CommonUtility

object PairRDDFunction {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val rdd = spark.sparkContext.parallelize(List("Germany India USA","USA India Russia","India Brazil Canada China"))
    val wordsRdd = rdd.flatMap(_.split(" "))
    val pairRDD = wordsRdd.map(f=>(f,1))
    pairRDD.foreach(println)

    pairRDD.distinct().sortByKey().foreach(println)

    val wordCount = pairRDD.reduceByKey((a,b)=> a+b)
    wordCount.foreach(println)


    def param1= (accu:Int,v:Int) => accu + v
    def param2= (accu1:Int,accu2:Int) => accu1 + accu2
    val wordCount2 = pairRDD.aggregateByKey(0)(param1,param2)
    wordCount2.foreach(println)

    println(wordCount2.count())


    pairRDD.collectAsMap().foreach(println)

  }
}
