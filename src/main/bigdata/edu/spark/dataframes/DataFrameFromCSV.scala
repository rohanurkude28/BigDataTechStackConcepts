package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{avg, col, when}

object DataFrameFromCSV {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    val df = spark.read.option("header", "true").option("sep", ",").option("inferSchema", "true").format("csv").load("src/main/resources/employees.csv")
    df.printSchema()

    val df1  = df.filter(col("DEPARTMENT_ID") === 50 && col("SALARY").cast("Int") >= 8000).drop("DEPARTMENT_ID")
    df1.show(false)

    val df2 = df.groupBy("DEPARTMENT_ID").agg(avg("SALARY").as("AVG_SALARY"))
    df2.printSchema()
    df2.show(false)

    val df3 = df2.sort(col("AVG_SALARY").cast("Int").desc).limit(5)
    df3.show(false)

    df3.coalesce(1).write.option("header", "true" ).mode(SaveMode.Overwrite).csv("src/main/resources/output/DFOps")

    println(df3.rdd.getNumPartitions)

    df.select(col("EMPLOYEE_ID"),col("GENDER")).show(false)

    df.select(col("GENDER")).distinct().show(false)

    df.select(col("EMPLOYEE_ID").desc).distinct().show(30,false)

    val newEmpDF = df.withColumn("EXPANDED_GENDER", when(col("GENDER") === "M", "MALE").when(col("GENDER") === "F", "FEMALE").otherwise("UNKNOWN")).drop("GENDER")
    newEmpDF.show(50, false)
  }
}
