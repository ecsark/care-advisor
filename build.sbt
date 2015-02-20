name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.neo4j" % "neo4j-kernel" % "2.1.6",
  "org.neo4j" % "neo4j-rest-graphdb" % "2.0.1",
  "com.sun.jersey" % "jersey-core" % "1.18.3",
  "org.neo4j" % "neo4j-cypher-dsl" % "2.0.1",
  "org.springframework" % "spring-context" % "4.1.3.RELEASE",
  "org.springframework.data" % "spring-data-neo4j" % "3.2.1.RELEASE",
  "org.springframework.data" % "spring-data-neo4j-rest" % "3.2.1.RELEASE"
  //"be.objectify" %% "deadbolt-java" % "2.3.1"
)


resolvers += "neo4j-releases" at "http://m2.neo4j.org/releases"
