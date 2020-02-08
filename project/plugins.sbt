
resolvers += Resolver.url(
  "tpolecat-sbt-plugin-releases",
    url("https://dl.bintray.com/content/tpolecat/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

addSbtPlugin("io.verizon.build" % "sbt-rig" % "5.0.39")

// docs
addSbtPlugin("com.typesafe.sbt"          % "sbt-site"     % "1.4.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-ghpages"  % "0.6.3")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.10")
addSbtPlugin("org.tpolecat"              % "tut-plugin"   % "0.6.13")
addSbtPlugin("com.eed3si9n"              % "sbt-unidoc"   % "0.4.3")
