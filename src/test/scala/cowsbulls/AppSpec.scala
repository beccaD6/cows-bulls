package cowsbulls
import cats.effect.unsafe.implicits.global
import cats.effect.{IO, Ref}
import org.specs2.mutable.Specification

class AppSpec extends Specification {
  //reference to an IO of a list, any modifications to this reference are guaranteed to not happen until the IO execution/unsafe run sync
  private val createOutputReference: IO[Ref[IO, List[String]]] = Ref.of(List.empty)

  private def createInputReference(inputLines: List[String]): IO[Ref[IO, List[String]]] = Ref.of(inputLines)

  private def createPrintlnFn(outputRef: Ref[IO, List[String]])(message: String): IO[Unit] = {
    outputRef.update { oldOutputList: List[String] =>
      message :: oldOutputList
    }
  }

  private def createReadlnFn(inputRef: Ref[IO, List[String]]): IO[String] = {
    inputRef.flatModify { oldInputList: List[String] =>
      oldInputList match {
        case Nil => (Nil, IO.raiseError(new RuntimeException("no more inputs")))
        case head :: tail => (tail, IO.pure(head))
      }
    }
  }

  private val answerGenerator = IO(List(1, 2, 3, 4))

  private def runApp(guesses: List[String]): IO[List[String]] = for {
    outputRef <- createOutputReference
    inputRef <- createInputReference(guesses)
    res <- new App(createPrintlnFn(outputRef), createReadlnFn(inputRef), answerGenerator).run()
    output <- outputRef.get
  } yield output


  "App" should {
    "give cow and bull count for incorrect guess" in {
      val output = runApp(List("1233","1234")).unsafeRunSync()
      output must contain("Make a guess","bulls: 3, cows: 1", "correct guess!")
    }
    "give errors for incorrect input then prompt user to guess again" in {
      val output = runApp(List("1233", "12e3","1", "1233", "1234")).unsafeRunSync()
      output must contain("Make a guess","bulls: 3, cows: 1", "Please provide a valid number", "Please provide a 4 digit number", "correct guess!")
    }
  }




}
