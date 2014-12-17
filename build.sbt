name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.neo4j" % "neo4j-kernel" % "2.1.6",
  "org.neo4j" % "neo4j-rest-graphdb" % "2.0.1",
  "com.google.inject" % "guice" % "4.0-beta",
  "com.sun.jersey" % "jersey-core" % "1.18.3",
  "com.google.code.gson" % "gson" % "2.3.1"
  //"be.objectify" %% "deadbolt-java" % "2.3.1"
)


resolvers += "neo4j-releases" at "http://m2.neo4j.org/releases"

//resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)