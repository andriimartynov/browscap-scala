organization := "net.github.andriimartynov.browscap"

name := "browscap-scala"

version := "0.0.1"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

lazy val root = (project in file("."))

lazy val benchmarks  = (project in file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .dependsOn(root)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.4" % Test,
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "org.scalacheck" %% "scalacheck" % "1.13.+" % Test,
  "org.mockito" % "mockito-all" % "1.10.19" % Test
)

credentials += Credentials(
  "Artifactory Realm",
  "oss.jfrog.org",
  sys.env.getOrElse("OSS_JFROG_USER", ""),
  sys.env.getOrElse("OSS_JFROG_PASS", "")
)

publishTo := {
  val jfrog = "https://oss.jfrog.org/artifactory/"
  if (isSnapshot.value)
    Some("OJO Snapshots" at jfrog + "oss-snapshot-local")
  else
    Some("OJO Releases" at jfrog + "oss-release-local")
}
