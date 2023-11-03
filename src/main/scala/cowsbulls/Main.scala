package cowsbulls

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = {
    val app : App = new App(IO.println, IO.readLine)
    runR(app)
  }

  final def runR(app: App): IO[ExitCode] = {
    app.run().map(_ => ExitCode.Success).handleErrorWith(_=>runR(app))
  }
}
