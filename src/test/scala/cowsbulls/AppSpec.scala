package cowsbulls

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO}
import org.specs2.mutable.Specification
class AppSpec extends Specification {

  "App" should {
    "prompt the user" in {
      var output : List[String] = List.empty
      def ourPrintln(message: String): IO[Unit] = {
        IO {
          output = message :: output
          ()
        }
      }
      val ourReadLn = IO.pure("1234")
      val app: App = new App(ourPrintln, ourReadLn)
      app.run().unsafeRunSync()
      output must contain("Make a guess")
    }
  }


  "tell the user when they give invalid input" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("12ee")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync() must throwA( new RuntimeException("Please provide a valid number"))
  }

  "tell the user when they give an incorrect amount of digits" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("23")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync() must throwA( new RuntimeException("Please provide a 4 digit number"))
  }


  "tell the user when they make a correct guess" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("1234")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync()
    output must contain("correct guess!")
  }

  "tell the user number of cows and bulls" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("1243")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync() must throwA( new RuntimeException("incorrect guess"))
    output must contain("bulls: 2, cows: 2")
    output must not contain("correct guess!")
  }

  "tell the user the number of cows and bulls" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("1230")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync() must throwA( new RuntimeException("incorrect guess"))
    output must contain("bulls: 3, cows: 0")
    output must not contain("correct guess!")
  }


  "tell the user the number of cows and bulls" in {
    var output: List[String] = List.empty

    def ourPrintln(message: String): IO[Unit] = {
      IO {
        output = message :: output
        ()
      }
    }

    val ourReadLn = IO.pure("4321")
    val app: App = new App(ourPrintln, ourReadLn)
    app.run().unsafeRunSync() must throwA( new RuntimeException("incorrect guess"))
    output must contain("bulls: 0, cows: 4")
    output must not contain("correct guess!")
  }
}
