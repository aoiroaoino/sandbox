name := """sbt-jmh-seed"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"
  "org.scalaz" %% "scalaz-core" % "7.1.4"
)

enablePlugins(JmhPlugin)
