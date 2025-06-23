import laika.ast.Path.Root
import laika.helium.Helium
import laika.helium.config._
import java.net.URL

ThisBuild / organization := "io.getnelson.quiver"
ThisBuild / organizationName := "Nelson Team"
ThisBuild / scalaVersion := Scala212Version
ThisBuild / crossScalaVersions := Seq(
  Scala212Version,
  Scala213Version,
  Scala3Version
)
ThisBuild / tlBaseVersion := "8.0"
// Pending a bot account or API key to add to GitHub
ThisBuild / githubWorkflowPublishTargetBranches := Nil

lazy val quiver = project
  .in(file("."))
  .aggregate(core.jvm, core.js, codecs.jvm, codecs.js, docs)
  .settings(
    publish / skip := true
  )

val CatsVersion         = "2.6.1"
val ScalacheckShapeless = "1.2.5"
val CollectionsCompat   = "2.13.0"
val ScodecVersion       = "1.11.7"
val Scodec2Version      = "2.0.0"
val Scala212Version     = "2.12.20"
val Scala213Version     = "2.13.16"
val Scala3Version       = "3.3.6"

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel"          %%% "cats-free"               % CatsVersion,
      "org.scala-lang.modules" %%% "scala-collection-compat" % CollectionsCompat,
      "org.typelevel"          %%% "cats-laws"               % CatsVersion % Test
    )
  )
  .settings(silenceUnusedImport)
  .settings(coverageEnabled := false)

val silenceUnusedImport = Seq(
  Compile / scalacOptions += "-Wconf:origin=scala.collection.compat._:s"
)

lazy val codecs = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("codecs"))
  .dependsOn(core % "test->test;compile->compile")
  .settings(
    libraryDependencies += {
      if (scalaVersion.value.startsWith("2."))
        "org.scodec"    %%% "scodec-core" % ScodecVersion
      else "org.scodec" %%% "scodec-core" % Scodec2Version
    }
  )
  .settings(
    Compile / unmanagedSourceDirectories ++= {
      if (scalaVersion.value.startsWith("2."))
        Seq(
          (ThisBuild / baseDirectory).value / "codecs" / "src" / "main" / "scala-2"
        )
      else Seq.empty
    }
  )
  .settings(coverageEnabled := false)

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core.jvm)
