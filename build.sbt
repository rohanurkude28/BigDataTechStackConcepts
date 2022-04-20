import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "edu.rohan"
ThisBuild / organizationName := "rohanurkude28"
val AkkaVersion = "2.6.19"

lazy val root = (project in file("."))
  .settings(
    name := "BigDataTechStackConcepts",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-stream" % AkkaVersion,"com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion),
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.4.8","org.apache.spark" %% "spark-sql" % "2.4.8")

  )
