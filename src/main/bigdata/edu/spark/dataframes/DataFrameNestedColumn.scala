package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object DataFrameNestedColumn {
  def main(args: Array[String]): Unit = {
    val spark = CommonUtility.getSparkSession()

    val schema = new StructType()
      .add("name",new StructType()
        .add("firstname",StringType)
        .add("middlename",StringType)
        .add("lastname",StringType))
      .add("dob",StringType)
      .add("gender",StringType)
      .add("salary",IntegerType)

    val data = Seq(Row(Row("James ","","Smith"),"36636","M",3000),
      Row(Row("Michael ","Rose",""),"40288","M",4000),
      Row(Row("Robert ","","Williams"),"42114","M",4000),
      Row(Row("Maria ","Anne","Jones"),"39192","F",4000),
      Row(Row("Jen","Mary","Brown"),"","F",-1)
    )

    val dataFrame =  spark.createDataFrame(spark.sparkContext.parallelize(data),schema)
    dataFrame.printSchema()
    dataFrame.show()

    val dataFrame2 = dataFrame.withColumn("fname",col("name.firstname"))
      .withColumn("mname",col("name.middlename"))
      .withColumn("lname",col("name.lastname"))
      .drop("name")
    dataFrame2.printSchema()

    dataFrame.select(col("name.firstname").as("fname"),
      col("name.middlename").as("mname"),
      col("name.lastname").as("lname"),
      col("dob"),col("gender"),col("salary"))
      .show()
  }
}
