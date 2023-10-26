package cowsbulls

//Most often, you need an object to hold methods and values/variables that shall be available without having to first instantiate an instance of some class. for groupign methods


object CowsAndBulls {
  val HerdSize = 4

  def cowsBulls(guessList: List[Int], actualNumberList: List[Int]) : CowsAndBullsResult = {
    val zipped: List[(Int, Int)] = actualNumberList.zip(guessList)

    val numBulls: Int = zipped.count {
      case (actuaLElem, guessElem) => (actuaLElem == guessElem)
    }
    val numCows: Int = zipped.count {
      case (actuaLElem, guessElem) => (actuaLElem != guessElem && actualNumberList.contains(guessElem))
    }

    CowsAndBullsResult(cows=Cows(numCows),bulls=Bulls(numBulls))
  }


}
