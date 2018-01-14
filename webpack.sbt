/*
lazy val webpack = TaskKey[Unit]("Run webpack when packaging the application")

def runWebpack(file: File) = {
  Process("webpack", file) !
}

webpack := {
  if(runWebpack(baseDirectory.value) != 0) throw new Exception("Something goes wrong when running webpack.")
}

dist <<= dist dependsOn webpack

stage <<= stage dependsOn webpack

test <<= (test in Test) dependsOn webpack
*/

import scala.sys.process.Process

lazy val webpack = taskKey[Unit]("Run webpack when packaging the application")

def runWebpack(file: File) = {
  Process("npm run build", file) !
}

webpack := {
  if(runWebpack(baseDirectory.value) != 0) throw new Exception("Something went wrong when running webpack.")
}

dist := (dist dependsOn webpack).value

stage := (stage dependsOn webpack).value