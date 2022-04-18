package edu.spark.rdd

import edu.spark.CommonUtility
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object RDDtoDF {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    import spark.implicits._

    val columns = Seq("language","users_count")
    val data = Seq(("Java", "20000"), ("Python", "100000"), ("Scala", "3000"))
    val rdd = spark.sparkContext.parallelize(data)

    val dfFromRDD1 = rdd.toDF()
    dfFromRDD1.printSchema()

    val dfFromRDD2 = rdd.toDF(columns:_*)
    dfFromRDD2.printSchema()

    val dfFromRDD3 = rdd.toDF("language","users_count")
    dfFromRDD3.printSchema()


    //From RDD (USING createDataFrame and Adding schema using StructType)
    val schema = StructType(columns
      .map(fieldName => StructField(fieldName, StringType, nullable = true)))
    //convert RDD[T] to RDD[Row]
    val rowRDD = rdd.map(attributes => Row(attributes._1, attributes._2))
    val dfFromRDD4 = spark.createDataFrame(rowRDD,schema)
    dfFromRDD4.printSchema()
  }
}
