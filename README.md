### Big Data Tech Stacks

- [Hadoop](Hadoop.md)
- [Scala](Scala.md)
- [Spark](Spark.md)
- [Interview Questions](InterviewQuestion.md)

### Spark vs Hadoop

Big data processing is just computation of iterative algorithms. Below is difference between Hadoop and Spark:

![](sections/resources/BigDataIteration.png)

- Spark uses the Hadoop MapReduce distributed computing framework as its foundation. Spark was intended to improve on several aspects of the MapReduce project, such as performance and ease of use while preserving many of MapReduce's benefits.
- Spark needs a lot of RAM for computation purposes, hence we should prefer it for AI/ML or streaming real time data
- Hadoop writes data to disk hence should be preferred for batch applications

<a href="https://phoenixnap.com/kb/hadoop-vs-spark/" target="_blank">Spark vs Hadoop Detailed comparison</a>

90% of the time is spent in hadoop in IOps, it also involves killing JVM per iteration and rebooting.

### Language in Spark : 
    
- Scala : Performance, type safety, Enterprise acceptance 
- Python : Learning curve, ML Lib, Visualisation Libs

### Latency issue in Distributed Systems 

Distribution introduces two issues

- Partial failure : crash failure of a subset of machines involved in distributed computation
- Latency : certain operation has higher latency than other operations due to network latency

![](sections/resources/LatencyNumbers.png)