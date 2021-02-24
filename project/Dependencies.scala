import sbt._

object Dependencies {
  private val AkkaVersion = "2.6.8"
  private val AkkaHttpVersion = "10.2.2"
  private val circeVersion = "0.12.3"
  private val logbackVersion = "1.2.3"
  private val scalaLoggingVersion = "3.9.2"

  val defaultDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.35.3",
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,

    "org.scalatest" %% "scalatest" % "3.2.2" % Test,
    "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test)
}
