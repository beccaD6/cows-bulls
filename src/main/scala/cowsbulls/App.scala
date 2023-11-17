package cowsbulls

import cats.effect.{ExitCode, IO}

class App(println: (String) => IO[Unit], readLine: IO[String]) {

  final def run(): IO[Unit] = {
    val singleRun = new SingleRun(println,readLine)
    singleRun.run().iterateUntil(x=>x).void
  }

}
