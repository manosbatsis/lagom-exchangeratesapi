name := "lagom-exchangeratesapi"
organization in ThisBuild := "com.github.manosbatsis"
version in ThisBuild := "1.0-SNAPSHOT"

// The Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"
// Enable cross-publishing, see https://www.scala-sbt.org/1.x/docs/Publishing.html#Cross-publishing
crossScalaVersions := Seq("2.11.11", "2.12.4")
publishTo := {
  val nexus = "https://oss.sonatype.org/content/repositories/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "/snapshots")
  else
    Some("releases"  at nexus + "/releases")
}
// Pickup nexus credentials from the filesystem
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
pomExtra :=
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
lagomUnmanagedServices in ThisBuild := Map("api-exchangeratesapi-io" -> "https://api.exchangeratesapi.io")


lazy val `lagom-exchangeratesapi` = (project in file("."))
  .aggregate(`external-service-api`,`external-service-impl`)

lazy val `external-service-api` = (project in file("external-service-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-service-impl` = (project in file("external-service-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`external-service-api`)