name := """test-scala-naumen"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"
PlayKeys.devSettings := Seq("play.server.http.port" -> "8888")

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0"


