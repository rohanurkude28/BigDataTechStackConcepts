package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.functions.expr

object DataFrameFromMemory {
  def main(args: Array[String]): Unit = {

    val spark = CommonUtility.getSparkSession()
    val dateList = CommonUtility.dateList
    val columns = Seq("date", "months_to_add")

    import spark.implicits._
    val dateDF = dateList.toDF(columns: _*)

    dateDF.printSchema()
    dateDF.show()

    val resultDF = dateDF.withColumn("new_Date", expr("add_months(date,months_to_add)")).drop("month_to_add")

    resultDF.printSchema()
    resultDF.show()
  }

}
