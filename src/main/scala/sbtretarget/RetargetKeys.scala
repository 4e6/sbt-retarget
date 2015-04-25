package sbtretarget

import sbt._

trait RetargetKeys {
  lazy val retargetBaseDirectory = settingKey[File]("Base retarget directory")
  lazy val retargetOrigin = settingKey[File]("Original target directory")
  lazy val retargetPrefix = settingKey[String]("Retarget directory prefix")
  lazy val retargetConfigFile = settingKey[File]("Sbt settings file for plugin")
  lazy val retargetCopyFiles = settingKey[Boolean]("Copy existing files of target directory")
}

object RetargetKeys extends RetargetKeys
