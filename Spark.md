//TODO: Index Creation

## Spark

- Distributed Processing Engine(Why distributes? For parallel processing) 
- Execution engine like MapReduce (Fault tolerant)
- In Memory Execution (Keeps all data immutable and in-memory)
- Sql Support
- Can read write data from any platform eg RDBMS, Datewarehouse, NoSql, SAP, MainFrame, Saleforce 
- Spark is a processing engine runs on Hadoop , HDFS primary storage, YARN cluster manager 
- Hadoop different tools - Pig (Scripting), Hive (SQL), Mahout(ML), Oozie(Workflow)
- Spark - Spark streaming, SQL, Mlib, GraphX, Spark Core (Execution Engine - RDD for batch processing)

- In the shared memory case, you have this data-parallel programming model, this collections programming model. And underneath the hood, the way that it's actually executed is that the data is partitioned in memory.And then operated upon in parallel by independent threads or using a thread pool or something like that
- In the distributed case, we have the same collection abstraction we did in parallel model on top of this distributed execution. But now instead we have data between machines, the network in between which is important. And just like in a shared memory case we still operate on that data in parallel. (Concern of latency between workers)

### Spark Architecture

- Resources will be given by Cluster Manager eg: [YARN](Hadoop.md#yet-another-resource-negotiator-yarn) 
- Resources are given inform of executor, Executor is combination of CPU and RAM.
- Data Node 1 - Driver program starts on one of the machine - contains settings to run the programs - resources, compressions, initialising the object . Will create spark context object. 
- Using this driver program will talk to cluster manager. 
  
  **_Once Driver has the resource based on the logic written in driver class, it will create a logical plan and then will create a DAG and then create an execution plan, as part of this it will divide the whole logic into stages and stages are further divided into tasks which are executed into nodes_**

![](sections/resources/SparkWorkflow.png)

![](sections/resources/SparkWorkFlowInternal.png)


- Spark uses concept called partitioning. partitions data and feeds to executors which then divides it into task 
- Spark Streaming - runs 24x7 unlike batch processing, challenge - resources - hence dynamic resource allocation
- Fault tolerant 
- Integration with other libraries

- Spark Streaming (RDDs) uses Java Serialisation which helps in catchpoint (restart in case of failure), but when there is upgrade it risks backward compatibility. 
- Hence, Spark Structured Streaming(Dataset and dataframes) was introduced.
- Spark Streaming - Micro Batch processing DStream (Batch Interval), each batch represents a RDD, 
- Spark Structured Streaming - Polls data after some duration, recevied data is triggered and appended in continous flow, Dataframes are more optimised

## Lineages

- Computation on RDDs are represented as lineage graph; a Directed Acyclic Graph (DAG) representing the computation done on the RDD
- Recovering from failures by recomputing lost partitions from Lineage Graphs
- Fault tolerant without having to write data to disk

## DAG

- Its set of edges and vertices, vertices represent RDDs and edges represent operation to be performed on RDDs
- Every edge directs from earlier to later in sequence
- On calling of _Action_ , created DAG is submitted to DAG scheduler which further splits graph into stages of the task based on the demarcation of shuffling, new stages is created based on the data shuffling requirement.
- Using DAG we can go any deep dive into whats happening in each stage. Scheduler splits the spark RDD into various stages based on transformation applied Narrow vs Wide.
- Each Stage has multiple tasks, these stages are based on partition of RDDs, so that same computation can be performed in parallel.

## Spark Context vs Spark Session

Spark Context :

- SparkContext is the entry point of Spark functionality.
- Spark context allows Spark Application to access Spark Cluster with the help of Resource Manager
```scala
val sparkConf = new SparkConf().setAppName("SparkContextExample").setMaster("local")
//create spark context object
val sc = new SparkContext(conf)

sc.textFile
sc.sequenceFile
sc.parallelize

sc.stop()
```

Spark Session :

- Spark session is a unified entry point of a spark application from Spark 2.0. It provides a way to interact with various spark’s functionality with a lesser number of constructs. Instead of having a spark context, hive context, SQL context, now all of it is encapsulated in a Spark session.
- Every user can work in an isolated way

```scala
val spark = SparkSession.builder
.appName("SparkSessionExample") 
.master("local[4]") 
.config("spark.sql.warehouse.dir", "target/spark-warehouse")
.enableHiveSupport()
.getOrCreate

spark.newSession()

spark.sparkContext.parallelize
```


### RDD (Resilient Distributed Dataset)

- Basic DS of Spark Framework
- Immutable distributed collection of objects
- Each dataset in RDD is divided into logical partitions, which may be computed on different on different nodes of cluster
- RDD can contain any type of object Java, Scala, Python, including user defined object
- All signature are same except **.aggregate** which has binding parameter (Call be ref) which can cause issue over network

### RDD Representation

- Partitions : Atomic piece of dataset. One or more per compute node.
- Dependencies : Model relation between this RDD and its Partitions with RDDs it was derived from
- A function for computing the dataset based on its parent RDD
- Metadata about partitioning scheming and data placement 

### Ways to create RDD

- Transform existing RDD
- From a SparkContext (or SparkSession) object
  * parallelize : convert a local Scala Collection to a RDD
  * textFile : read a text file from HDFS or a local file system and return  an RDD of string

### Types of RDD

- Parallel Collection RDD - is a RDD of a collection of elements with number of partitions. **sc.parallelize(1 to 10, 2)**
- Shuffled RDD - is a key value pair that represents shuffle step in RDD lineage. These RDDs are created after RDD transformations that trigger data shuffling across nodes in cluster
- Pair RDD -  is a key value pair where in similar operations needs to be performed on each keys. **rdd.map(x => (x.1,x.2))**
- Hadoop RDD - provide core functionalities for reading data stored in HDFS, SparkContext: Hadoop file, text file, sequence file


### Operations in RDD

Two types of data ops:
1. Transformations will return a new RDD and are **lazy ops** . eg: filter, map, flatMap, distinct. union, intersection, subtract, cartesiN
2. Action will return a value and are **eager ops** .  eg: collect, count, take, reduce, foreach. takeSample, takeOrdered, saveAsTextFile, saveAsSequenceFile

**foreach is a eager action but return unit hence it executes on executor and not driver whereas take returns a type A hence it executes on driver Node**

Dataframes - is a Dataset organised into named columns,  equivalent to a RDBMS table, with richer optimisation underhood.


### Caching and Persist

```scala
val lastYearLogs : RDD[String] = ???
val logsWithError = lastYearLogs.filter(log => log.contains("ERROR")).persist() //logsWithError will be called N times if we don't persist
val first10LogsWithError = logsWithError.take(10)
val numErrors = logsWithError.count()
```

Possible to persist data set:
- In memory as regular Java Objects
- On disk as regular Java Objects
- In memory as regular Serialised Java Objects (More Compact)
- On disk as regular Serialised Java Objects (More Compact)
- both in memory and on disk (spill over to disk to avoid re computation)

Cache : Shorthand for using default storage level, which is in-memory only as regular java objects
Persist : Persistence can be customised with this method. Pass the storage level you'd like as a parameter to persist.

![](sections/resources/CachingNPersistLevels.png)

- Cache is warpper on top of Persist API `Cache() = Persist(StaorageLevel.MEMORY_ONLY)`
- By Default Cache is in Memory and is a deserialized object
- Spark UI -> Storage (Fraction Memory -> Percentage of data stored, Size on memory, Size on disk)

```scala
import scala.util._
import org.apache.spark.sql.functions._

val df1 = Seq.fill(50)(Random.nextInt()).toDF("C1")
val df2 = df1.withColumn("C2",rand()).join(df1,"C1").cache()
val df3 = df2.withColumn("C3",rand()).join(df1,"C2").persist(StaorageLevel.DISK_ONLY)
val df4 = df3.withColumn("C4",rand()).join(df1,"C3")
val df5 = df4.withColumn("C5",rand()).join(df1,"C4")
val df6 = df5.withColumn("C6",rand()).join(df1,"C5")

```

- [Scala : fold vs foldLeft - Why fold can run in parallel?](https://stackoverflow.com/questions/16111440/scala-fold-vs-foldleft)
- Why Serial foldLeft/foldRight doesn't exist on Spark? Ans : Doing things serially across is difficult. Lots of Synchronisation. Doesn't make a lot of sense

![](sections/resources/TypeErrorFoldLeftParallel.png)

TODO Create A Mapping of Transformations and Actions with clauses:

- sortWith 
- aggregate ![](sections/resources/AggregateParallel.png) in accumulator we would waste lot of memory and time to carry all unrelated fields 
- groupByKey  - index (Transformation - lazy)
- reduceByKey - more efficient than groupByKey and then reduce     (Transformation - lazy)
- countByKey  - no of elements per key  (Transformation - lazy)
- mapValues   - only applies to Pair RDD (Action - eager) (org, budget) mapValues (org, (budget,1))
- keys - (Transformation - lazy)
- join - (Transformation - lazy)
- leftOuterJoin/rightOuterJoin 
- collect sortBy 
- mapPartitions 
- mapParallel 
- reduce

## Shuffle

- Shuffles can be an enormous hit to performance because it means that Spark has to move a lot of its data around the network and remember how important latency is.
- A Shuffle can occur when the resulting RDD depends on other elements from same RDD or another RDD

### GroupByKey

![](sections/resources/GroupByKey-ClusterDataDistribution.png)

### ReduceByKey

![](sections/resources/ReduceByKey-ClusterDataDistribution.png)

Transformation causes shuffle. There are two kinds
- Narrow : when each partition of the parent RDD is used by at most one partition of the child RDD. (Fast, No shuffle, Optimisation like pipelining possible)
- Wide :  each partition of the parent RDD may be depended on by multiple children partitions. (Slow, require some data to be shuffled over network)

![](sections/resources/NarrowNWideTransformation.png)
 
## Partitioning

The data within RDD are split into multiple Spark partitions

### Properties of Partitions

- Partitions never span multiple machines i.e. tuples in same partitions are guaranteed to be on same machine
- Each machine in cluster contain one or more partitions
- Number of partition to use is configurable. By default, It's equal to total number of cores on all executor nodes

Two Types of Partitions:
- Hash Partitioning
- Range Partitioning
- **Custom partitioning only possible on Pair RDDs**

How to Set Partitioning?
- by calling partitionBy on RDD
- using transformations that return RDD with specific partitioner

**map/flatMap operations loses partitioning in result RDD - cause map and flatMap can change the Keys, hence use mapValues**

### Optimising with Partitioner

Partitioning can bring enormous performance gains, especially in the face of operations that may cause shuffles. The basic intuition is that if you can somehow optimize for data locality, then you can prevent a lot of network traffic from even happening.


## Structure vs Unstructured Data

- Object blobs and HOF falls into unstructured and spark can't look inside it and hence can't optimise. (We have to do it)
- Database/Hive are structured and Spark can optimise on its own

## Spark SQL

![](sections/resources/SparkSQL.png)

Three main APIs:
- SQL Literal Syntax
- Dataframes
- Datasets

Two specialised backend components:
- Catalyst, query optimiser
- Tungsten, off-heap serialiser

### Data Frames

- Dataframes is Spark SQL's abstraction
- Dataframes, are conceptually RDDs full of records with a know schema (Kind of like table)
- Dataframes are Untyped
- Transformations are Untyped

Ways to create a dataframe:
- From existing RDD .toDF
- Reading a specific datasource from a file

```scala
case class Department(id: Int,dname:String)
case class Employee(id: Int,fname:String,lname:String,age:Int,city:String,deptId:Int)

val depts = List(Department(1000,"Finance"),Department(1001,"IT"),Department(1002,"HR"),Department(1003,null))
val employees = List(Employee(1,"John","Doe",21,"Sydney",1000),Employee(2,"Jane","Doe",32,"Melbourne",1001),Employee(3,"Jack","Daniel",25,"Sydney",1001),Employee(4,"James","Bond",32,"Victoria",1002),Employee(5,"Jason","Bourne",41,"Sydney",1002),Employee(6,"Jamie","Lannister",51,null,1000),Employee(7,"Jake","Gyllenhaal",28,"Sydney",1004))

val deptDF = sc.parallelize(depts).toDF
val employeeDF = sc.parallelize(employees).toDF

deptDF.show()
employeeDF.show()

val sydneyEmployeeDF =employeeDF.select("id","fname").where("city == 'Sydney'").orderBy("id")
sydneyEmployeeDF.show()

val youngCityEmployeeDF =employeeDF.groupBy("city").min("age")
youngCityEmployeeDF.show()

import org.apache.spark.sql.functions._
val aggCityEmployeeDF =employeeDF.groupBy($"city").agg(count($"city")).orderBy($"count(city)".desc)
aggCityEmployeeDF.show()

val avgCityEmployeeDF =employeeDF.groupBy($"city").agg(sum($"age")/count($"age"))
avgCityEmployeeDF.show()

val joinEmployeeWithDept = employeeDF.join(deptDF, deptDF("id")===employeeDF("deptId")).drop(deptDF("id")).orderBy($"id") //Doesn't give error if given wrong column Name

joinEmployeeWithDept.show()

val leftJoinEmployeeWithDept = employeeDF.join(deptDF, deptDF("id")===employeeDF("deptId"),"left_outer").drop(deptDF("id")).orderBy($"id") //Doesn't give error if given wrong column Name

leftJoinEmployeeWithDept.show()
```

### DF Optimization

Catalyst Optimiser
- Reordering operations
- Reduce the amount of data we must read
- Pruning unneeded Partitions

Tungsten Optimiser
- highly specialise data encoder (tightly serialised hence can keep more data into memory)
- column based
- off heap (free from garbage collection overhead)

### DataSets

- Data Frames are DataSets
- `type DataFrame = Dataset[Row]`
- typed distributed collection of data
- mix RDD and DataFrame operations



Ways to create a DataSets:
- From existing DF.toDS
- Reading a specific datasource from a file. `spark.read.json(""").as[Person]`

```scala
val tupleList = List((1,"One"),(2,"Two"),(3,"Three"),(4,"Four"))
val tupleListRDD = sc.parallelize(tupleList)

tupleListRDD.reduceByKey(_ + _).collect

// equivalent in DS

val tupleListDS = tupleList.toDS
tupleListDS.groupByKey(_._1).mapGroups((k,vs) => (k,vs.foldLeft("")((acc,p)=> acc + p._2))).show()
tupleListDS.groupByKey(_._1).mapValues(_._2).reduceGroups((acc,p)=> acc+p).show()
```

### [DF vs DS vs RDD](https://phoenixnap.com/kb/rdd-vs-dataframe-vs-dataset)

![](sections/resources/WhenToUseWhat.png)

### Spark Performance Tuning : TODO

#### Executor tuning
- Leave aside one core per node : For several daemons running in background like NameNode, Secondary NameNode, DataNode, JobTracker, Task Tracker, Application master etc.
- HDFS Throughput : HDFS client has trouble with tons of concurrent threads, Too many tasks per executor causes Huge GC overheads. It was observed HDFS achieves full write throughput with **5 cores/tasks per executor**
- YARN Application Master : 1GB and 1 Executor for AM
- Memory Overhead : Full memory requested to yarn per executor = spark-executor-memory + spark-yarn-executor-memoryOverhead
  spark-yarn-executor-memoryOverhead = Max(384MB, 7% of spark-executor-memory)
  eg : IF we request for 20GB per executor, AM will get 20GB + memoryOverhead = 20 + 7% of 20 = 23 GB
- Running executor with too much memory often results in excessive GC delays
- Running tiny executors throws away benefits that comes from running multiple JVM 

[Spark By Example](https://sparkbyexamples.com/spark)


//TODO: Handling data skewness in spark