name := "scala-sampler"

version := "0.9"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-deprecation", "-feature")

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.7"

EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.Unmanaged, EclipseCreateSrc.Source, EclipseCreateSrc.Resource)
