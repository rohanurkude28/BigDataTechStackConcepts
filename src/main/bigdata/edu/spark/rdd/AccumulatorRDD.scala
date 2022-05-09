package edu.spark.rdd

import edu.spark.CommonUtility

object AccumulatorRDD {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val longAcc = spark.sparkContext.longAccumulator("SumAccumulator")
    val rdd = spark.sparkContext.parallelize(Array(1, 2, 3))

    rdd.foreach(x => longAcc.add(x))
    println(longAcc.value)
  }
}
