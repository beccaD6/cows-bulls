package cowsbulls

import cats.effect.{ExitCode, IO}

class App(println: (String) => IO[Unit], readLine: IO[String], answerGenerator: IO[List[Int]]) {

  final def run(): IO[Unit] = {
    for {
      answer <- answerGenerator
      singleRun = new SingleRun(println,readLine, answer)
      _ <- singleRun.run().iterateUntil(x=>x)
    } yield ()
  }

}
