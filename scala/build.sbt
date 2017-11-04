name := "church"

scalaVersion := "2.11.7"

initialCommands in console :=
  """|//import Church._
     |val cm= reflect.runtime.currentMirror
     |val u = cm.universe
     |val tb=scala.tools.reflect.ToolBox(cm).mkToolBox()
     |import LambdaCalc._
     |import Term._
     |import SampleTerms._
     |import scalaz.syntax.show._
     |import scalaz.syntax.equal._
     |""".stripMargin


libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.4" % "test")

scalacOptions ++= Seq("-Yrangepos")

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.4"

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _)

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)