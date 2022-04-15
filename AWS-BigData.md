## AWS EMR (Elastic Map reduce)

- Webservice to process vast amount of data.
- Pay as you use.
- Fast and efficient processing of big data
- Compute and processing happens on EC2 and storage is on S3.
- Can move large data in and out (Transform ETL) of AWS data stores like DB, Dynamo DB, S3 etc.

### EMR Clusters

- **MASTER NODE :** The master node tracks the status of tasks and monitors the health of the cluster. Every cluster has a master node, and it's possible to create a single-node cluster with only the master node.
- **CORE NODE :** A node with software components that run tasks and store data in the Hadoop Distributed File System (HDFS) on your cluster. Multi-node clusters have at least one core node.
- **TASK NODE :** A node with software components that only runs tasks and does not store data in HDFS. Task nodes are optional.

List of services EMR Integrates with :
- EC2
- VPC
- S3
- Cloudwatch for Logging
- IAM
- Cloud Trail for Auditing
- AWS Data Pipelines
- EC2 Key pairs for SSH


- EMR provides flexibility to scale your cluster up or down as your computing needs change.
- You can resize your cluster to add instances for peak workloads and remove instances to control costs
  when peak workloads subside.
- EMR also provides the option to run multiple instance groups so that you can:
  – Use On-Demand Instances in one group for guaranteed processing power
  – Use Spot Instances in another group to have your jobs completed faster and for lower costs.
  – You can also mix different instance types to take advantage of better pricing for one Spot Instance type over another
- Amazon EMR launches all nodes for a given cluster in the same Amazon EC2 Availability Zone.
- Running a cluster in the same zone improves performance of the jobs flows because it provides a
  higher data access rate.

- Amazon EMR makes use of YARN (Yet Another Resource Negotiator) by default.
- Users may utilize YARN scheduling capabilities like FairScheduler (queueMaxAppsDefault setting) or CapacityScheduler to achieve complicated scheduling and resource management of concurrent tasks.
- Ganglia gives a web-based user interface that allows us to see the metrics that Ganglia has captured. The web interface runs on the master node once we run Ganglia on Amazon EMR, and it can be browsed through port forwarding (SSH tunnel).


### EMR Data Processing

- Load data to be processed by EMR to S3
- Customers upload their input data into Amazon S3.
  – Amazon EMR then launches a number of Amazon EC2 instances as specified by the customer.
  – EMR pulls the data from Amazon S3 into the launched Amazon EC2 instances.
  – Once the cluster is finished, Amazon EMR transfers the output data to Amazon S3, where
  customers can then retrieve it or use as input in another cluster
- The Hadoop application can also load the data from anywhere on the internet or from other AWS services

### EMR Encryption and Logs

- Amazon EMR supports optional Amazon S3 server-side and client-side encryption with EMRFS to help protect the data that you store in Amazon S3.With server-side encryption, Amazon S3 encrypts your data after you upload it.
- EMR always uses HTTPS to move data between S3 and EMR cluster’s EC2 instances
- Hadoop system logs as well as user logs will be placed in the Amazon S3 bucket which you specify when creating a cluster.
- Also, EMR integrates with CloudTrail and can have all API calls logged to an S3 bucket of your choice




[EMR Deployment](https://cm.engineering/automating-deployments-for-sparks-structured-streaming-on-aws-emr-cf940d6a588a)