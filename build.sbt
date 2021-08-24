import java.net.URL

lazy val quiver = project
  .in(file("."))
  .aggregate(core.jvm, core.js, codecs.jvm, codecs.js, docs)
  .settings(
    skip in publish := true
  )

val CatsVersion         = "2.6.1"
val ScalacheckShapeless = "1.2.5"
val CollectionsCompat   = "2.5.0"
val ScodecVersion       = "1.11.7"
val Scala212Version     = "2.12.14"
val Scala213Version     = "2.13.6"

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel"              %%% "cats-free"                 % CatsVersion,
      "org.scala-lang.modules"     %%% "scala-collection-compat"   % CollectionsCompat,
      "org.typelevel"              %%% "cats-laws"                 % CatsVersion         % Test,
      "com.github.alexarchambault" %%% "scalacheck-shapeless_1.14" % ScalacheckShapeless % Test
    )
  )
  .settings(commonSettings)
  .settings(silenceUnusedImport)
  .settings(coverageEnabled := scalaBinaryVersion.value != "2.13")
  .jsSettings(coverageEnabled := false)

val silenceUnusedImport = Seq(
  scalacOptions += "-Wconf:cat=unused-imports&site=quiver:s,any:wv",
  scalacOptions += "-Wconf:cat=unused-imports:s,any:wv"
)

val commonSettings = Seq(
  organization := "io.getnelson.quiver",
  scalaVersion := Scala212Version,
  crossScalaVersions := Seq(Scala212Version, Scala213Version)
)

lazy val codecs = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("codecs"))
  .dependsOn(core % "test->test;compile->compile")
  .settings(
    libraryDependencies += "org.scodec" %%% "scodec-core" % ScodecVersion
  )
  .settings(commonSettings)
  .settings(coverageEnabled := scalaBinaryVersion.value != "2.13")
  .jsSettings(coverageEnabled := false)

lazy val docsMappingsAPIDir = settingKey[String](
  "Name of subdirectory in site target directory for api docs"
)
lazy val docs = project
  .in(file("quiver-docs"))
  .dependsOn(core.jvm, codecs.jvm)
  .enablePlugins(MdocPlugin, MicrositesPlugin, ScalaUnidocPlugin)
  .settings(commonSettings)
  .settings(
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inProjects(
      core.jvm,
      codecs.jvm
    ),
    docsMappingsAPIDir := "api",
    addMappingsToSiteDir(
      mappings in (ScalaUnidoc, packageDoc),
      docsMappingsAPIDir
    ),
    mdocVariables := {
      val stableVersion: String =
        version.value.replaceFirst("[\\+\\-].*", "")
      Map("VERSION" -> stableVersion)
    },
    mdocIn := (baseDirectory in ThisBuild).value / "docs" / "mdoc",
    micrositeName := "Quiver - a Scala graph library",
    micrositeUrl := "https://getnelson.github.io",
    micrositeDocumentationUrl := "/quiver/api/index.html",
    micrositeDocumentationLabelDescription := "API Documentation",
    micrositeBaseUrl := "/quiver"
  )
