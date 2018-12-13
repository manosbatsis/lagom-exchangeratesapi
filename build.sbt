organization in ThisBuild := "com.github.manosbatsis"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
lagomUnmanagedServices in ThisBuild := Map("external-service" -> "https://api.exchangeratesapi.io")


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