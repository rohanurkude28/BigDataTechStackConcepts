package edu.spark.dataset

import edu.spark.CommonUtility
import org.apache.spark.sql.types.{DoubleType, LongType}

object DataSetOps {

  case class Department(DEPARTMENT_ID:Long, DEPARTMENT_NAME: String, MANAGER_ID: Long, LOCATION_ID: Long)

  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    import spark.implicits._

    spark.read
      .option("header",true)
      .option("inferSchema",true)
      .option("sep",",")
      .format("csv")
      .load("src/main/resources/departments.csv")
      .withColumn("MANAGER_ID", 'MANAGER_ID.cast(LongType)) //For Type Any
      .as[Department]
      .show()
  }
}
