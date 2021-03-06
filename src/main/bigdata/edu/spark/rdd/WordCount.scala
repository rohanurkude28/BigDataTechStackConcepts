package edu.spark.rdd

import edu.spark.CommonUtility
import org.apache.spark.Partitioner
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SaveMode

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    val rdd = spark.sparkContext.textFile("src/main/resources/Essay.txt")
    rdd.collect().foreach(println)

    // rdd flatMap transformation
    val rdd2 = rdd.flatMap(f=>f.split(" "))
    rdd2.foreach(f=>println(f))

    //Create a Tuple by adding 1 to each word
    val rdd3:RDD[(String,Int)]= rdd2.map(m=>(m,1))
    rdd3.foreach(println)

    //Filter transformation
    val rdd4 = rdd3.filter(a=> a._1.startsWith("a"))
    rdd4.foreach(println)

    //ReduceBy transformation
    val rdd5 = rdd3.reduceByKey(_ + _)
    rdd5.foreach(println)

    //Swap word,count and sortByKey transformation
    val rdd6 = rdd5.map(a=>(a._2,a._1)).sortByKey()
    println("Final Result")

    //Action - foreach
    rdd6.foreach(println)

    //Action - count
    println("Count : "+rdd6.count())

    //Action - first
    val firstRec = rdd6.first()
    println("First Record : "+firstRec._1 + ","+ firstRec._2)

    //Action - max
    val datMax = rdd6.max()
    println("Max Record : "+datMax._1 + ","+ datMax._2)

    //Action - reduce
    val totalWordCount = rdd6.reduce((a,b) => (a._1+b._1,a._2))
    println("dataReduce Record : "+totalWordCount)
    //Action - take
    val data3 = rdd6.take(3)
    data3.foreach(f=>{
      println("data3 Key:"+ f._1 +", Value:"+f._2)
    })

    //Action - collect
    val data = rdd6.collect()
    data.foreach(f=>{
      println("Key:"+ f._1 +", Value:"+f._2)
    })

    //Action - saveAsTextFile
    rdd5.repartition(1).saveAsTextFile("src/main/resources/output/wordCount/")

    val partitionData = rdd5.partitionBy(new CustomPartition(4))
    partitionData.saveAsTextFile("src/main/resources/output/wordCountCustom/")
  }

  class CustomPartition(numPart : Int) extends Partitioner{
    override def numPartitions: Int = numPart

    override def getPartition(key: Any): Int = {
      key.toString toUpperCase match {
        case "THE" => 0
        case "IS" => 1
        case "FOR" => 2
        case _ => 3
      }
    }
  }
}
