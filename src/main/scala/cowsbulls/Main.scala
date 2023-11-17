package cowsbulls

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = {
    val app : App = new App(IO.println, IO.readLine)
    app.run().map(_=>ExitCode.Success)
  }


}
