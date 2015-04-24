package sbtretarget

import java.io.File

import sbt._, Keys._
import java.nio.file.Files

object RetargetPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends RetargetKeys
  import autoImport._

  lazy val baseRetargetSettings: Seq[Def.Setting[_]] = Seq(
    retargetBaseDirectory := IO.temporaryDirectory,
    retargetPrefix := "sbt_retarget_" + name.value + "_",
    retargetConfigFile := baseDirectory.value / "retarget.sbt",
    retargetCopyFiles := false,
    commands ++= Seq(Retarget.retarget, Retarget.retargetCleanup)
  )

  override lazy val projectSettings = baseRetargetSettings
}

object Retarget {

  def retarget = Command.command("retarget") { state =>
    val x = Project.extract(state); import x._

    val name = get(Keys.name)
    val target = get(Keys.target)
    val retargetBase = get(RetargetKeys.retargetBaseDirectory)
    val retargetPrefix = get(RetargetKeys.retargetPrefix)
    val retargetConfigFile = get(RetargetKeys.retargetConfigFile)
    val retargetCopy = get(RetargetKeys.retargetCopyFiles)

    if (isRelativeTo(target, retargetBase)) {
      state.log.info(s"already retarget $name to $target")
      state
    } else {
      val temp = Files.createTempDirectory(retargetBase.toPath, retargetPrefix).toFile
      if (retargetCopy) IO.copyDirectory(target, temp, overwrite = true, preserveLastModified = true)
      ensureSymlink(target, temp)
      state.log.info(s"Set target to $temp")
      saveTargetSetting(state, temp, retargetConfigFile)
    }
  }

  def retargetCleanup = Command.command("retargetCleanup") { state =>
    val x = Project.extract(state); import x._

    val name = get(Keys.name)
    val target = get(Keys.target)
    val retargetBase = get(RetargetKeys.retargetBaseDirectory)
    val retargetConfigFile = get(RetargetKeys.retargetConfigFile)
    val retargetCopy = get(RetargetKeys.retargetCopyFiles)

    if (isRelativeTo(target, retargetBase)) {
      IO.delete(retargetConfigFile)
      IO.delete(target)
      val updated = x.append(Seq(Keys.target := get(baseDirectory) / "target"), state)
      Project.runTask(Keys.clean, updated)
      updated
    } else {
      state.log.info(s"nothing to undo for $name")
      state
    }
  }

  def aggregate(state: State, withCurrent: Boolean): Seq[ProjectRef] = {
    val x = Project.extract(state); import x._
    val aggs = currentProject.aggregate
    if (withCurrent) currentRef +: aggs else aggs
  }

  def saveTargetSetting(state: State, target: File, retargetFile: File): State = {
    val targetSetting = s"""target := file("${target.getAbsolutePath}")"""
    IO.writeLines(retargetFile, Seq(targetSetting), append = false)
    s"set $targetSetting" :: state
  }

  def ensureSymlink(link: File, target: File): File =
    if (Files.isSymbolicLink(link.toPath)) link
    else {
      if (link.exists) IO.delete(link)
      Files.createSymbolicLink(link.toPath, target.toPath).toFile
    }

  private def isRelativeTo(file: File, directory: File): Boolean =
    file.getAbsolutePath startsWith directory.getAbsolutePath


}

trait RetargetKeys {
  lazy val retargetBaseDirectory = settingKey[File]("Base retarget directory")
  lazy val retargetPrefix = settingKey[String]("Retarget directory prefix")
  lazy val retargetConfigFile = settingKey[File]("Sbt file where new target setting saved")
  lazy val retargetCopyFiles = settingKey[Boolean]("Copy existing target directory")
}
object RetargetKeys extends RetargetKeys
