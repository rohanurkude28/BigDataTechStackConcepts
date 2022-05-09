package edu.spark

import org.apache.spark.sql.SparkSession

object CommonUtility {
  case class Salary(deptName: String, empId: Int, salary: Int)

  val numList = List(1,2,3,4,5,6,7,8,9,10)
  val namesList = List((0,"John"),(1,"James"),(2,"Jack"),(3,"Jason"),(4,"Jamie"))
  val dateList = List(("2022-10-28",2),("2022-01-09",5),("2022-02-02",6),("2022-04-30",4),("2022-07-24",8))
  val empSalary = Seq(Salary("sales", 1, 5000), Salary("hr", 2, 3900), Salary("sales", 3, 4000), Salary("sales", 4, 4000)
    , Salary("hr", 5, 3500), Salary("tech", 7, 5200), Salary("tech", 8, 7000), Salary("tech", 9, 3500), Salary("tech", 10, 6200), Salary("tech", 11, 6200))

  def getSparkSession()={
    val sparkSession = SparkSession.builder().appName("SparkSession").master("local").getOrCreate()
    sparkSession.sparkContext.hadoopConfiguration.set("spark.hadoop.validateOutputSpecs", "false")

    sparkSession
  }
}
