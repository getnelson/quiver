enablePlugins(TutPlugin)
enablePlugins(ScalaUnidocPlugin)
enablePlugins(GhpagesPlugin)
enablePlugins(DisablePublishingPlugin)

// for whatever reason addMappingsToSiteDir insists on a setting key and not a value
val tutSiteDir = settingKey[String]("site directory for tut file mappings (defaults to empty string for top-level)")
tutSiteDir := ""
val unidocSiteDir = settingKey[String]("site directory for unidoc")
unidocSiteDir := "api"

addMappingsToSiteDir(tut, tutSiteDir)

addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), unidocSiteDir)

ghpagesNoJekyll := false

includeFilter in makeSite := "*.yml" | "*.md" | "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.swf"

git.remoteRepo := "git@github.com:oncue/quiver.git"

scalacOptions in (ScalaUnidoc, unidoc) ++= Seq(
  "-doc-source-url", "https://github.com/oncue/quiver/blob/master€{FILE_PATH}.scala",
  "-sourcepath", baseDirectory.in(LocalRootProject).value.getAbsolutePath,
  "-groups",
  "-implicits"
)
