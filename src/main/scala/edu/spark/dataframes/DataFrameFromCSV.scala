package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.functions.{col, column, expr, when}

object DataFrameFromCSV {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val employeeDF = spark.read.option("header", "true").csv("src/main/resources/employees.csv")

    employeeDF.printSchema()
    employeeDF.select(employeeDF.col("EMPLOYEE_ID"), col("FIRST_NAME"), column("LAST_NAME"), expr("GENDER")).show(50, false)

    val newEmpDF = employeeDF.withColumn("EXPANDED_GENDER", when(col("GENDER") === "M", "MALE").when(col("GENDER") === "F", "FEMALE").otherwise("UNKNOWN")).drop("GENDER")
    newEmpDF.show(50, false)
  }
}
