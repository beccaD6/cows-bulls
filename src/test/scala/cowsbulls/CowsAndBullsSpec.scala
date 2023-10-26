package cowsbulls

import cowsbulls.CowsAndBulls.cowsBulls
import org.specs2.mutable.Specification

class CowsAndBullsSpec extends Specification {

  "CowsAndBulls" should {
    "return correct cows and bull count when guess is correct" in {

      val actualList = List(1,2,3,4)
      val guessList = List(1,2,3,4)

      val result: CowsAndBullsResult = cowsBulls(guessList,actualList)
      result must beEqualTo(CowsAndBullsResult(Cows(0),Bulls(4)))
      result.isCorrect must beTrue
    }

    "return correct cows and bull count when correct numbers in wrong places" in {

      val actualList = List(1, 2, 3, 4)
      val guessList = List(4, 3, 2, 1)

      val result: CowsAndBullsResult = cowsBulls(guessList, actualList)
      result must beEqualTo(CowsAndBullsResult(Cows(4), Bulls(0)))
      result.isCorrect must beFalse
    }

    "return correct cows and bull count when incorrect numbers" in {

      val actualList = List(1, 2, 3, 4)
      val guessList = List(5, 6, 7, 1)

      val result: CowsAndBullsResult = cowsBulls(guessList, actualList)
      result must beEqualTo(CowsAndBullsResult(Cows(1), Bulls(0)))
      result.isCorrect must beFalse
    }
  }

}
