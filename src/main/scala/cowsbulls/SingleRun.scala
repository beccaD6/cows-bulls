package cowsbulls

import cats.implicits._
import cats.effect.{ExitCode, IO}
import cowsbulls.CowsAndBulls.{HerdSize, cowsBulls}
import cowsbulls.StringToIntList.stringToIntList

import scala.util.Try
import scala.util.control.NonFatal

class SingleRun(println: (String) => IO[Unit], readLine: IO[String]) {

  private def handleResult(result: Either[AppError, CowsAndBullsResult]): IO[Unit] = {
    result match {
      case Left(appError) => println(appError.message)
      case Right(cowsAndBullsResult) => {
        if (cowsAndBullsResult.isCorrect) {
          println("correct guess!")
        } else {
          println(cowsAndBullsResult.toString())
        }
      }
    }
  }

  def run(): IO[Boolean] = {
    val res: IO[Either[AppError, CowsAndBullsResult]] = fetchGuessList().flatMap {
      errorOrGuessList =>

        val result: Either[AppError, CowsAndBullsResult] = errorOrGuessList.map { guessList =>
          val actualList = List(1, 2, 3, 4) //IO(randomNumber())
          cowsBulls(guessList, actualList)
        }
        handleResult(result).map( _ => result)
    }

    res.map {
      case Right(cowsAndBulls) => cowsAndBulls.isCorrect
      case Left(_) => false
    }

  }


  private def fetchGuessList(): IO[Either[AppError, List[Int]]] = {
    for {
      _ <- println("Make a guess")
      userInput <- readLine
      _ <- println(s"your guess was $userInput")
    } yield stringToIntList(userInput)
  }


  def randomNumber(): List[Int] = {
    val list: List[Int] = (0 to 9).toList
    scala.util.Random.shuffle(list).take(HerdSize)
  }


}

