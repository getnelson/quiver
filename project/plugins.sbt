resolvers += Resolver.url(
  "tpolecat-sbt-plugin-releases",
  url("https://dl.bintray.com/content/tpolecat/sbt-plugin-releases")
)(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n"       % "sbt-unidoc"               % "0.4.3")
addSbtPlugin("org.scalameta"      % "sbt-mdoc"                 % "2.2.22")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.19.0")
addSbtPlugin("com.47deg"          % "sbt-microsites"           % "1.4.4")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"            % "1.8.2")
addSbtPlugin("org.typelevel"      % "sbt-typelevel"            % "0.8.0")
addSbtPlugin("org.typelevel"      % "sbt-typelevel-site"       % "0.8.0")
