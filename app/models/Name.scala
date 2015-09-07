package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Name(firstName: String, lastName: String)

object Name{
  implicit val nameFormat: Format[Name] = (
    (__ \ "firstName").format[String] and
      (__ \ "lastName").format[String]
    )(Name.apply _, unlift(Name.unapply))
}

