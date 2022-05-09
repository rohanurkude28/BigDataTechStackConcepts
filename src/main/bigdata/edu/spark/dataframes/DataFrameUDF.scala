package edu.spark.dataframes

import edu.spark.CommonUtility
import org.apache.spark.sql.functions.col

object DataFrameUDF {
  def main(args: Array[String]): Unit = {

    val spark = CommonUtility.getSparkSession()
    import spark.implicits._
    val namesDF = CommonUtility.namesList.toDF("ID","NAME")
    val upperCaseFn: String => String = _.toUpperCase

    import org.apache.spark.sql.functions.udf

    val upperCaseUDF = udf(upperCaseFn)
    namesDF.withColumn("upperCaseData",upperCaseUDF(col("NAME"))).show()


    namesDF.createOrReplaceTempView("NAMES")

    spark.udf.register("upper_case",upperCaseFn)

    val udfDF = spark.sql("Select *,upper_case(NAME) as UPPERNAME  from NAMES").where('UPPERNAME === "JAMES")

    println(udfDF.explain(true))
    udfDF.show()

    val dF = spark.sql("Select * from NAMES").where('NAME === "James")

    println(dF.explain(true))
    dF.show()

  }
}
