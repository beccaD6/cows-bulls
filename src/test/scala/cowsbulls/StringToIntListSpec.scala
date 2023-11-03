package cowsbulls

import cats.implicits.toBifunctorOps
import cowsbulls.StringToIntList.stringToIntList
import org.specs2.mutable.Specification

class StringToIntListSpec extends Specification {
  "StringToIntList" should {
  "convert a string to a list of ints" in {
    val res = stringToIntList("1234")
    res must beRight(List(1,2,3,4))
  }

  "return a left of an error when the string contains non numeric characters" in {
    val res = stringToIntList("12e4")

    res.leftMap(_.getMessage) must beLeft("Please provide a valid number")
  }
}

}
