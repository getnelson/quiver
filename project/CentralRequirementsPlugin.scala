package verizon.build

import sbt._, Keys._

object CentralRequirementsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    developers := List(
      Developer(
        "timperett",
        "Timothy Perrett",
        "@timperett",
        java.net.URI.create("https://github.com/timperrett").toURL(),
      ),
      Developer(
        "runarorama",
        "Runar Bjarnason",
        "@runarorama",
        java.net.URI.create("https://github.com/runarorama").toURL(),
      ),
      Developer(
        "stew",
        "Stew O'Connor",
        "@stew",
        java.net.URI.create("https://github.com/stew").toURL(),
      ),
      Developer(
        "rossabaker",
        "Ross A. Baker",
        "@rossabaker",
        java.net.URI.create("https://rossabaker.com/").toURL(),
      ),
    ),
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
