ThisBuild / organization := "dev.aoiroaoino"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.1"

lazy val root = (project in file("."))
  .settings(name := "lang-stone")
  .aggregate(core)
  .dependsOn(core)

lazy val core = project
  .settings(moduleName := "lang-store-core")
