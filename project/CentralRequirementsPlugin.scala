package verizon.build

import sbt._, Keys._

object CentralRequirementsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    pomExtra in Global := {
      <developers>
        <developer>
          <id>timperrett</id>
          <name>Timothy Perrett</name>
          <url>http://github.com/timperrett</url>
        </developer>
        <developer>
          <id>runarorama</id>
          <name>Runar Bjarnason</name>
          <url>http://github.com/runarorama</url>
        </developer>
        <developer>
          <id>stew</id>
          <name>Stew O'Connor</name>
          <url>http://github.com/stew</url>
        </developer>
      </developers>
    },
    licenses := Seq(
      "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")
    ),
    homepage := Some(url("http://getnelson.github.io/quiver/")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/getnelson/quiver"),
        "git@github.com:getnelson/quiver.git"
      )
    ),
    startYear := Some(2013)
  )
}
