name := "scala-sampler"

version := "0.9"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-deprecation", "-feature")

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.0"
