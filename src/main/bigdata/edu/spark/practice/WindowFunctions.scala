package edu.spark.practice

import edu.spark.CommonUtility
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.{col, expr, row_number}

object WindowFunctions {

  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()
    import spark.implicits._

    val empSalary = CommonUtility.empSalary.toDS()

    empSalary.createOrReplaceTempView("empSalary")

    spark.sqlContext.sql("select deptName,empId, salary, max(salary) over (partition by deptName) - salary as diff from empSalary").show()

    val window = Window.partitionBy(empSalary.col("deptName")).orderBy(empSalary.col("empId"))
    empSalary.withColumn("row_number",row_number.over(window)).show()
    empSalary.withColumn("salary_diff",functions.max("salary").over(window) - col("salary"))
      .show()

  }
}
