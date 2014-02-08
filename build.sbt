name := "church"

scalaVersion := "2.10.2"

initialCommands in console :=
  """|import Church._
     |val cm= reflect.runtime.currentMirror
     |val u = cm.universe
     |val tb=scala.tools.reflect.ToolBox(cm).mkToolBox()
     |""".stripMargin


libraryDependencies += "org.specs2" %% "specs2" % "2.2"

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _)

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)