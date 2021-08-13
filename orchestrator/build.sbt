name := "orchestrator"
version := "0.1"
scalaVersion := "2.12.8"

val AkkaVersion = "2.6.9"
val AlpakkaVersion = "2.0.1"
val AlpakkaKafkaVersion = "2.0.5"
val Json4s = "3.6.11"
val FgeValidatorVersion = "2.2.6"

lazy val AkkaAlpakka = Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-elasticsearch" % AlpakkaVersion,
  "com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
)

lazy val Config = Seq(
  "com.typesafe" % "config" % "1.4.1"
)

lazy val Logging = Seq(
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
)

lazy val Json = Seq(
  "org.json4s" %% "json4s-ext" % Json4s,
  "org.json4s" %% "json4s-native" % Json4s,
)

lazy val LocalDeps = Seq(
  "com.vladimirbaklan" %% "microservices-shared" % "0.1-SNAPSHOT"
)

libraryDependencies ++= AkkaAlpakka ++ Config ++ Json ++ Logging ++ LocalDeps