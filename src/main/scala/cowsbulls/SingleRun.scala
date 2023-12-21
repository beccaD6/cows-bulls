package cowsbulls


import cats.Monad
import cats.implicits._
import cats.effect.{ExitCode, IO}
import cowsbulls.CowsAndBulls.{HerdSize, cowsBulls}
import cowsbulls.StringToIntList.stringToIntList

//tagless final
class SingleRun[F[_]: Monad](println2: (String) => F[Unit], readLine: F[String], correctAnswer: List[Int]) {

  private def fetchGuessList(): F[Either[AppError, List[Int]]] = {
    for {
      _ <- println2("Make a guess")
      userInput <- readLine
      _ <- println2(s"your guess was $userInput")
    } yield stringToIntList(userInput)
  }

  private def handleResult(result: Either[AppError, CowsAndBullsResult]): F[Boolean] = {
    result match {
      case Left(appError) => println2(appError.message).map(_=>false)
      case Right(cowsAndBullsResult) => {
        if (cowsAndBullsResult.isCorrect) {
          println2("correct guess!").map(_=>true)
        } else {
          println2(cowsAndBullsResult.toString()).map(_=>false)
        }
      }
    }
  }


  def run(): F[Boolean] = {

    for {
      errorOrGuessList <- fetchGuessList()
      errorOrCowsBullsResult <- Monad[F].pure(errorOrGuessList.map(guessList => cowsBulls(guessList,correctAnswer)))
      result <- handleResult(errorOrCowsBullsResult)
    } yield result
//   fetchGuessList().flatMap {
//        errorOrGuessList =>
//
//          val result: Either[AppError, CowsAndBullsResult] = errorOrGuessList.map { guessList =>
//            cowsBulls(guessList, correctAnswer)
//          }
//          handleResult(result)
//    }

  }






}

