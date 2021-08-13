name := "microservices-shared"

ThisBuild / organization := "com.vladimirbaklan"
ThisBuild / version      := "0.1-SNAPSHOT"

Global / onChangedBuildSource := ReloadOnSourceChanges

val AkkaVersion = "2.6.9"
val AlpakkaVersion = "2.0.1"
val AlpakkaKafkaVersion = "2.0.5"
val Json4s = "3.6.11"

resolvers += "twttr" at "https://maven.twttr.com/"
resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)

resolvers := ("Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository") +: resolvers.value

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

lazy val Jooq =
  Seq(
    "org.postgresql" % "postgresql" % "42.2.23",
    "com.zaxxer" % "HikariCP" % "3.3.1",
    "org.jooq" % "jooq" % "3.12.4",
    "org.jooq" %% "jooq-scala" % "3.12.4",
  )

libraryDependencies ++= AkkaAlpakka ++ Config ++ Json ++ Logging ++ Jooq