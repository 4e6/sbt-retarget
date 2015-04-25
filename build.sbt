sbtPlugin := true

name := "sbt-retarget"

organization := "me.4e6"

version := "0.1.1-SNAPSHOT"

licenses := Seq("MIT License" -> url("http://opensource.org/licenses/MIT"))

scalacOptions := Seq(
  "-encoding", "UTF-8",
  "-feature", "-deprecation", "-unchecked",
  "-target:jvm-1.7")

publishMavenStyle := false
