package cowsbulls

import cowsbulls.CowsAndBulls.HerdSize

case class CowsAndBullsResult(cows: Cows, bulls: Bulls) {
  val isCorrect: Boolean = bulls==Bulls(HerdSize)

  override def toString: String = s"bulls: ${bulls.value}, cows: ${cows.value}"
}
