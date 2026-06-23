ThisBuild / organization := "io.getnelson.quiver"
ThisBuild / organizationName := "Nelson Team"
ThisBuild / scalaVersion := Scala212Version
ThisBuild / crossScalaVersions := Seq(
  Scala212Version,
  Scala213Version,
  Scala3Version
)
ThisBuild / tlBaseVersion := "9.0"
// Pending a bot account or API key to add to GitHub
ThisBuild / githubWorkflowPublishTargetBranches := Nil

lazy val quiver = tlCrossRootProject
  .aggregate(core, codecs, docs)

val CatsVersion       = "2.13.0"
val CollectionsCompat = "2.14.0"
val ScodecVersion     = "1.11.11"
val Scodec2Version    = "2.3.3"
val Scala212Version   = "2.12.21"
val Scala213Version   = "2.13.18"
val Scala3Version     = "3.3.8"

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel"          %%% "cats-free"               % CatsVersion,
      "org.scala-lang.modules" %%% "scala-collection-compat" % CollectionsCompat,
      "org.typelevel"          %%% "cats-laws"               % CatsVersion % Test
    )
  )

lazy val codecs = crossProject(JSPlatform, JVMPlatform, NativePlatform)
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

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core.jvm)
