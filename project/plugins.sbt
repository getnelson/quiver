resolvers += Resolver.url(
  "tpolecat-sbt-plugin-releases",
  url("https://dl.bintray.com/content/tpolecat/sbt-plugin-releases")
)(Resolver.ivyStylePatterns)

addSbtPlugin("io.verizon.build"          % "sbt-rig"                  % "5.0.39"   excludeAll(
    ExclusionRule(organization = "org.scoverage")
))
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"             % "0.1.20")
addSbtPlugin("com.eed3si9n"              % "sbt-unidoc"               % "0.4.3")
addSbtPlugin("org.scalameta"             % "sbt-mdoc"                 % "2.2.22")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"              % "1.19.0")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"             % "2.4.3")
addSbtPlugin("com.47deg"                 % "sbt-microsites"           % "1.3.4")
addSbtPlugin("org.scoverage"             % "sbt-scoverage"            % "1.8.2")
