import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "edu.rohan"
ThisBuild / organizationName := "rohanurkude28"

lazy val root = (project in file("."))
  .settings(
    name := "BigDataTechStackConcepts",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.5",
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.4.8","org.apache.spark" %% "spark-sql" % "2.4.8")

  )
