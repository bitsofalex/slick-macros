import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.ebiznext.slickmacros",
    version := "0.0.1-SNAPSHOT",
    scalacOptions ++= Seq(),
//    scalacOptions ++= Seq("-Yshow-trees-stringified", "-Yshow-trees-compact", "-Xprint:parser",  "-Ystop-after:parser"),
    scalaVersion := "2.10.3",
//    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full)
  //  addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise_2.10.3-RC3" % "2.0.0-SNAPSHOT")
  )
}

object MacroBuild extends Build {
  import BuildSettings._

  lazy val macros = Project(
    "slickmacros",
    file("."),
    settings = buildSettings ++ Seq(
    publishTo <<= version { (v: String) =>
  		val nexus = "https://oss.sonatype.org/"
		if (v.trim.endsWith("SNAPSHOT"))
    		Some("snapshots" at nexus + "content/repositories/snapshots")
	  	else
		    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
	},
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    pomExtra := (
  <url>https://github.com/ebiznext/slick-macros</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:ebiznext/slick-macros.git</url>
    <connection>scm:git:git@github.com:ebiznext/slick-macros.git</connection>
  </scm>
  <developers>
    <developer>
      <id>hayssams</id>
      <name>Hayssam Saleh</name>
      <url>http://www.ebiznext.com</url>
    </developer>
  </developers>
),
      libraryDependencies ++= Seq(
	 "com.typesafe.slick" %% "slick" % "2.0.0",
   "org.scalamacros" % "quasiquotes" % "2.0.0-M3" cross CrossVersion.full,
   "joda-time" % "joda-time" % "2.3",
   "org.joda" % "joda-convert" % "1.2",
 
//  "com.typesafe.slick" %% "slick" % "1.0.1",
// "postgresql" % "postgresql" % "9.1-901.jdbc4",
 "org.scala-lang" % "scala-compiler" % "2.10.3",
 "org.scala-lang" % "scala-reflect" % "2.10.3")
  ))
}

