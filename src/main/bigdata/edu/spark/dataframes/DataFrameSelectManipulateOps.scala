package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.functions.{col, to_date}

object DataFrameSelectManipulateOps {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    val employeeDataDF = spark.read.option("header","true").option("inferSchema","true").option("sep",",").csv("src/main/resources/employees.csv")
    employeeDataDF.printSchema()

    employeeDataDF.createOrReplaceTempView("EMPLOYEE")
    spark.sql("Select * from EMPLOYEE").show()

    employeeDataDF.withColumnRenamed("HIRE_DATE","HIRE_DATE_STRING").show()

    //Not Working
    employeeDataDF.withColumn("SALARY_IN_DOLLARS",col("SALARY")/72).withColumn("HIRE_DATE_CHANGED",to_date(col("HIRE_DATE").cast("timestamp"),"ddMMyyyy").as("to_date")).show(false)
  }
}
