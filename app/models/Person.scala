package models

import akka.util.Helpers.Requiring
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Format._

case class Person(age: Int, name: Name, bloodType:Option[String], favoriteNumbers: Seq[FavoriteNumber] )

object Person{
  implicit val personFormat: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name] and
      (__ \ "bloodType").formatNullable[String] and
      (__ \ "favoriteNumbers").format[Seq[FavoriteNumber]]
    )(Person.apply _, unlift(Person.unapply))
}