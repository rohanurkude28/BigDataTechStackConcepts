# Spark

1. [Hadoop vs Spark?](https://www.ibm.com/cloud/blog/hadoop-vs-spark)
2. [What is Spark?](Spark.md/#spark)
3. [Why Spark is faster than Hadoop ?](README.md/#spark-vs-hadoop)
4. [Language to choose while working on spark?](README.md/#language-in-spark)
5. [Explain Spark Architecture?](Spark.md/#spark-architecture)
6. [What are latency problems in distributed systems?](README.md/#latency-issue-in-distributed-systems)
7. [Spark Context vs Spark Session?](Spark.md/#spark-context-vs-spark-session)
8. [YARN Client Mode vs Cluster Mode](Hadoop.md/#yarn-client-mode-vs-cluster-mode)
9. [Driver Vs Executor](Spark.md/#driver-vs-executor)
10. [Executor Vs Executor Core](Spark.md/#executor-vs-executor-core)
11. [Logical vs Physical Plan Spark](https://blog.knoldus.com/understanding-sparks-logical-and-physical-plan-in-laymans-term/)
12. [What is a RDD?](Spark.md/#rdd-resilient-distributed-dataset)
13. [What makes spark fault tolerant?](Spark.md/#lineages)
14. Yarn vs Spark Fault Tolerance : 
- YARN manages failure of resources/container/machine it manages that. Assigns resources to job and schedules job on different machine. Initially if Application Manager fails Resource manager use to kill all the container, but recently YARN has capability to rebind running container to newly launched Application Manager.
- Spark is fault tolerant in terms of execution. It makes plan of data and executes it , in cases of failure it retries by creating data again using lineage.
15. [Transformations vs Actions](Spark.md/#operations-in-rdd)
16. Map vs FlatMap
- Map is One to One operation.
- Flatmap is One to Many Operation.
17. [Map vs MapPartition](https://sparkbyexamples.com/spark/spark-map-vs-mappartitions-transformation/)
```scala
val newRd = myRdd.mapPartitions(partition => {
  // Creates a db connection per partition. This object will be cached inside each executor JVM. For the first time, the //connection will be created and hence forward, it will be reused. 
  // Very useful for streaming apps
  val connection = new DbConnection

  val newPartition = partition.map(record => {
    readMatchingFromDB(record, connection)
  }).toList // consumes the iterator, thus calls readMatchingFromDB 

  connection.close()
  newPartition.iterator // create a new iterator
})
```
18. [Wide vs Narrow Transformation](Spark.md/#narrow-vs-wide-transformation)
19. [In which case Wide Transformation doesn't shuffle data?](https://stackoverflow.com/a/43753997)
20. [What is Spark Dataframe internally?](Spark.md/#dataSets)
21. What is primary interface to create DataFrame is? DataFrameReader
22. What is spark glom ? rdd.glom().map - operation used for reading data in a single partition - array/matrix of data.
23. [DF vs DS vs RDD](Spark.md/#df-vs-ds-vs-rdd)
24. Why Data Sets are type safe? 
25. [Window Function in Spark?](https://sparkbyexamples.com/spark/spark-sql-window-functions/)
26. [Difference between ROW_NUMBER, RANK, DENSE_RANK and NTILE](https://coderscay.blogspot.com/2018/01/difference-between-rownumber-rank.html)
27. [Partitions vs Bucketing](https://www.youtube.com/watch?v=BHFgd-Q2AHM&list=PL9sbKmQTkW04_MDKPE8eEwOhYD1-G0Nox&index=39)

















Case Study:

Problem Statement: PV Consulting is one of the top consulting firms for big data projects. They mostly help big and small companies to analyze their data.
For Spark and Hadoop MR application, they started using YARN as a resource manager. Your task is to provide the following information for any job which is submitted to YARN using YARN console and YARN Cluster UI:

Who has submitted the job?
To which YARN queue is a job submitted?
How much time did it take to finish the job?
List of all running jobs on YARN
How to kill a job using YARN?
Check logs using YARN
Objective: To understand how we can monitor the submitted job on Yarn.
