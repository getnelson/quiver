import java.net.URL

lazy val quiver = project
  .in(file("."))
  .aggregate(core.jvm, core.js, codecs.jvm, codecs.js, docs)
  .settings(
    publish / skip := true
  )

val CatsVersion         = "2.6.1"
val ScalacheckShapeless = "1.2.5"
val CollectionsCompat   = "2.5.0"
val ScodecVersion       = "1.11.7"
val Scodec2Version      = "2.0.0"
val Scala212Version     = "2.12.14"
val Scala213Version     = "2.13.6"
val Scala3Version       = "3.0.1"

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
  .settings(commonSettings)
  .settings(silenceUnusedImport)
  .settings(coverageEnabled := scalaBinaryVersion.value == "2.13")
  .jsSettings(coverageEnabled := false)

val silenceUnusedImport = Seq(
  scalacOptions ++= {
    if (scalaBinaryVersion.value.startsWith("2."))
      Seq(
        "-Wconf:cat=unused-imports&site=quiver:s,any:wv",
        "-Wconf:cat=unused-imports:s,any:wv"
      )
    else Seq.empty
  }
)

val commonSettings = Seq(
  organization := "io.getnelson.quiver",
  scalaVersion := Scala212Version,
  crossScalaVersions := Seq(Scala212Version, Scala213Version, Scala3Version)
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
  .settings(commonSettings)
  .settings(coverageEnabled := scalaBinaryVersion.value == "2.13")
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
    crossScalaVersions -= Scala3Version,
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(
      core.jvm,
      codecs.jvm
    ),
    docsMappingsAPIDir := "api",
    addMappingsToSiteDir(
      ScalaUnidoc / packageDoc / mappings,
      docsMappingsAPIDir
    ),
    mdocVariables := {
      val stableVersion: String =
        version.value.replaceFirst("[\\+\\-].*", "")
      Map("VERSION" -> stableVersion)
    },
    mdocIn := (ThisBuild / baseDirectory).value / "docs" / "mdoc",
    micrositeName := "Quiver - a Scala graph library",
    micrositeUrl := "https://getnelson.github.io",
    micrositeDocumentationUrl := "/quiver/api/index.html",
    micrositeDocumentationLabelDescription := "API Documentation",
    micrositeBaseUrl := "/quiver"
  )
