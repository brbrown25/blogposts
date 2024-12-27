Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val V = new {
  val cats = "2.12.0"
  val catsEffect = "3.5.7"
  val doobie = "1.0.0-RC6"
  val postgres = "42.7.4"
  // test
  val munit = "1.0.0"
  val munitCatsEffect = "2.0.0"
  val scalacheck = "1.18.1"
  val scalacheckEffectMunit = "2.0.0-M2"
  val testContainers = "0.41.5"
  // compiler plugin
  val betterMonadicFor = "0.3.1"
  val kindProjector = "0.13.3"
}

lazy val root = (project in file("."))
  .settings(
    name := "testcontainers-tutorial",
    idePackagePrefix := Some("io.ssc"),
    libraryDependencies ++= Seq(
      "org.tpolecat" %% "doobie-core" % V.doobie,
      "org.tpolecat" %% "doobie-postgres" % V.doobie,
      "org.postgresql" % "postgresql" % V.postgres,
      "org.typelevel" %% "cats-core" % V.cats,
      "org.typelevel" %% "cats-effect" % V.catsEffect,
      "org.typelevel" %% "cats-effect-kernel" % V.catsEffect,
      "org.typelevel" %% "cats-kernel" % V.cats,
      "com.dimafeng" %% "testcontainers-scala-munit" % V.testContainers % "test",
      "com.dimafeng" %% "testcontainers-scala-postgresql" % V.testContainers % "test",
      "org.typelevel" %% "munit-cats-effect" % V.munitCatsEffect % "test",
      "org.scalameta" %% "munit-scalacheck" % V.munit % "test"
    )
  )
