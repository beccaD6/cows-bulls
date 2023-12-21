package cowsbulls

import cats.effect.IO
import cowsbulls.CowsAndBulls.HerdSize
import cats.effect.std.Random

object AnswerGenerator {

  def generateAnswer() : IO[List[Int]] = {
    val list: List[Int] = (0 to 9).toList
    for {
      random <- Random.scalaUtilRandom[IO]
      shuffleList <- random.shuffleList(list)
    } yield shuffleList.take(HerdSize)
  }

}
