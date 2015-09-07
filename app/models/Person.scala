package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Format._
import play.api.libs.json._

case class Person(age: Int, name: Name, bloodType: Option[String], favoriteNumbers: Seq[Int])

object Person {
  implicit val personFormat: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name] and
      (__ \ "bloodType").formatNullable[String] and
      (__ \ "favoriteNumbers").format[Seq[Int]]
    )(Person.apply _, unlift(Person.unapply))
}