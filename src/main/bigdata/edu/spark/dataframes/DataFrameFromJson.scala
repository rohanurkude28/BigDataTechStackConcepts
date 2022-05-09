package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.functions.{col, column, expr}

object DataFrameFromJson {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val employeeDF = spark.read.option("multiline", "true").json("src/main/resources/Employee.json")

    employeeDF.show()
    employeeDF.printSchema()
    employeeDF.select("id", "name", "userId").show()
    employeeDF.select(employeeDF.col("id"), col("userId"), column("name"), expr("description")).show()
  }
}
