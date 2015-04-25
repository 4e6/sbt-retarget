package sbtretarget

import sbt._, Keys._

object RetargetPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends RetargetKeys
  import autoImport._

  lazy val baseRetargetSettings: Seq[Def.Setting[_]] = Seq(
    retargetBaseDirectory := IO.temporaryDirectory,
    retargetOrigin := baseDirectory.value / "target",
    retargetPrefix := "sbt_retarget_" + name.value + "_",
    retargetConfigFile := baseDirectory.value / "retarget.sbt",
    retargetCopyFiles := true,
    commands ++= Seq(Retarget.retarget, Retarget.retargetRevert)
  )

  override lazy val projectSettings = baseRetargetSettings
}


