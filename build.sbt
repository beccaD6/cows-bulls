ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"



lazy val root = (project in file("."))
  .settings(
    name := "cows-bulls"
  )

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.2"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.10.0"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.20.2"