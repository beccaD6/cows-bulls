package cowsbulls
import cats.implicits._
import cats.effect.{ExitCode, IO}
import cowsbulls.CowsAndBulls.{HerdSize, cowsBulls}
import cowsbulls.StringToIntList.stringToIntList

import scala.util.Try
import scala.util.control.NonFatal

class App(println : (String) => IO[Unit], readLine: IO[String]) {

  def run(): IO[Unit] = {
   val result : IO[Unit] = for {
      guessList <- fetchGuessList()
      actualList = List(1,2,3,4) //IO(randomNumber())
      res = cowsBulls(guessList,actualList)
      _<- if(res.isCorrect){
        println("correct guess!")
      }  else {
        println(res.toString())
      }

    } yield ()

    result.attempt.flatMap {
      //print the error now/at this level, note println is an IO and we need to use flatmap to forward the error as a IO.raiseError not IO(IO.raiseError)
      case Left(e) => { println(e.getMessage).flatMap( _ => IO.raiseError(e)) }
      case Right(_) => IO()
    }
  }

  private def fetchGuessList(): IO[List[Int]] = {
    for {
      _ <- println("Make a guess")
      userInput <- readLine
      _ <- println(s"your guess was $userInput")
      guessList <- IO.fromEither(stringToIntList(userInput))
    } yield guessList
  }




  def randomNumber() : List[Int] = {
    val list  : List[Int] = (0 to 9).toList
    scala.util.Random.shuffle(list).take(HerdSize)
  }


}

