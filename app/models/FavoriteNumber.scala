package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class FavoriteNumber(number: Int)

object FavoriteNumber {
  implicit val favoriteNumberFormat: Format[FavoriteNumber] =
    __.format[Int].inmap(number => FavoriteNumber(number), (favoriteNumber: FavoriteNumber) => favoriteNumber.number)
}