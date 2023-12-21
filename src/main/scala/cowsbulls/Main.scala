package cowsbulls

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    val app : App = new App(IO.println, IO.readLine, AnswerGenerator.generateAnswer)
    app.run()
  }

}
