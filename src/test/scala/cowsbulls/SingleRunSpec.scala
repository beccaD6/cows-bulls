package cowsbulls

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, Ref}
import org.specs2.mutable.Specification
class SingleRunSpec extends Specification {


    //reference to an IO of a list, any modifications to this reference are guarenteed to not happen until the IO execution/unsafe run sync
    private val createOutputReference: IO[Ref[IO, List[String]]] = Ref.of(List.empty)

    private def createPrintlnFn(outputRef: Ref[IO, List[String]])(message: String): IO[Unit] = {
      outputRef.update { oldOutputList: List[String] =>
        message :: oldOutputList
      }
    }


    private def runApp(guess: String): IO[List[String]] = for {
      outputRef <- createOutputReference
      _ <- new SingleRun(createPrintlnFn(outputRef), IO.pure(guess), List(1,2,3,4)).run()
      output <- outputRef.get
    } yield output


//    var output: List[String] = List.empty
//
//    def ourPrintln(message: String): IO[Unit] = {
//      IO {
//        output = message :: output
//        ()
//      }
//    }
//
//    val ourReadLn = IO.pure(guess)
//    val app: App = new App(ourPrintln, ourReadLn)
//    app.run().map(_=>output)


  "App" should {
    "prompt the user" in {
      val output = runApp("1234").unsafeRunSync()
      output must contain("Make a guess")
    }
  }


  "tell the user when they give invalid input" in {
    val output = runApp("12ee").unsafeRunSync()
    output must contain("Please provide a valid number")
  }

  "tell the user when they give an incorrect amount of digits" in {
    val output = runApp("23").unsafeRunSync()
    output must contain("Please provide a 4 digit number")
  }


  "tell the user when they make a correct guess" in {

    val output = runApp("1234").unsafeRunSync()
    output must contain("correct guess!")

  }

  "tell the user number of cows and bulls" in {
    val output = runApp("1243").unsafeRunSync()
    output must contain("bulls: 2, cows: 2")
    output must not contain ("correct guess!")
  }

  "tell the user the number of cows and bulls" in {
    val output = runApp("1230").unsafeRunSync()
    output must contain("bulls: 3, cows: 0")
    output must not contain("correct guess!")
  }


  "tell the user the number of cows and bulls" in {
    val output = runApp("4321").unsafeRunSync()
    output must contain("bulls: 0, cows: 4")
    output must not contain("correct guess!")
  }
}
