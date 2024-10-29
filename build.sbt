import java.nio.file.Paths

import scala.sys.process._

import sbt.Keys._
import sbt.Keys.streams

Global / semanticdbVersion := "4.9.9"
ThisBuild / scalaVersion   := "2.12.20"
ThisBuild / organization   := "net.gemelen.dev.xonai"

ThisBuild / semanticdbEnabled := true
ThisBuild / scalacOptions ++=
  Seq(
    "-encoding",
    "utf8",
    "-deprecation"
  )

ThisBuild / Compile / run / fork := true
lazy val challenge = project
  .in(file("."))
  .settings(
    name := "challenge",
    libraryDependencies ++= Seq()
  )

lazy val decompileFF    = taskKey[Unit]("Decompile with FernFlower (JB)")
lazy val decompileCFR   = taskKey[Unit]("Decompile with CFR")
lazy val decompile      = taskKey[Unit]("Decompile")
lazy val scalap         = taskKey[Unit]("Run scalap against class files in target")
lazy val scalapInternal = taskKey[Unit]("Run scalap against class files in target")

scalapInternal := {
  val log = streams.value.log

  Process(
    Seq(
      "scalap",
      "-private",
      "-verbose",
      "-cp",
      "./target/scala-2.12/classes/org/apache/spark/sql/catalyst/plans/physical",
      "HashPartitioning"
    )
  ).!(log)
}

scalap := scalapInternal.dependsOn(Compile / compile).value

decompileFF := {
  val log        = streams.value.log
  val jarFile    = (Compile / packageBin).value.absolutePath
  val classesDir = (Compile / classDirectory).value.absolutePath
  val outputDir  = ((Compile / target).value / "ff").absolutePath
  Process(Seq("mkdir", "-p", outputDir)).!(log)
  Process(
    Seq(
      "java",
      "-jar",
      "./lib/fernflower.jar",
      "-log=warn",
      "-dgs=1",
      "-rbr=0",
      "-rsy=0",
      "-iec=1",
      classesDir,
      outputDir
    )
  ).!(log)
}
decompileCFR := {
  val log       = streams.value.log
  val jarFile   = (Compile / packageBin).value.absolutePath
  val outputDir = ((Compile / target).value / "cfr").absolutePath
  val cp = (Compile / dependencyClasspathAsJars).value
    .filter(_.data.getPath().contains("scala-library"))
    .head
    .data
    .absolutePath
  Process(
    Seq(
      "java",
      "-jar",
      "./lib/cfr.jar",
      "--elidescala",
      "true",
      "--forcetopsort",
      "true",
      "--silent",
      "true",
      "--hidelangimports",
      "false",
      "--extraclasspath",
      cp,
      "--outputdir",
      outputDir,
      jarFile
    )
  ).!(log)
}
decompile := {
  decompileFF.dependsOn(Compile / packageBin).dependsOn(clean).value
  decompileCFR.dependsOn(Compile / packageBin).dependsOn(clean).value
}
