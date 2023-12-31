package cowsbulls

import cats.implicits.{toBifunctorOps, toTraverseOps}
import cowsbulls.CowsAndBulls.HerdSize

import scala.util.Try

object StringToIntList {


  def stringToIntList(str: String): Either[AppError, List[Int]] = {
    val array = str.split("").toList
    if (array.length != HerdSize)
      Left(AppError("Please provide a 4 digit number"))
    else
      array.traverse { c =>
        Try(c.toInt).toEither.leftMap(_ => AppError("Please provide a valid number"))
      }

  }

}
