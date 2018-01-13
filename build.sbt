name := """rote"""
organization := "com.rote"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "mysql" % "mysql-connector-java" % "5.1.16",

  // slick integration
  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",

  // webjars support
  "org.webjars" %% "webjars-play" % "2.6.3",
  "org.webjars.bower" % "react" % "16.1.0",
  "org.webjars.npm" % "babel-core" % "6.26.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.rote.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.rote.binders._"
