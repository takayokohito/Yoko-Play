package controllers

import models.{Name, Person}
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import play.api.libs.functional.syntax._

class PersonApplication extends Controller {

  implicit val personFormat: Format[Person] = (
    (__ \ "age").format[Int] and
      (__ \ "name").format[Name]
    )(Person.apply _, unlift(Person.unapply))

  implicit val nameFormat: Format[Name] = (
    (__ \ "firstName").format[String] and
      (__ \ "lastName").format[String]
    )(Name.apply _, unlift(Name.unapply))

  def inputPersonInfo = Action(parse.json) { request =>
    request.body.validate[Person].map {
      p =>cd
        Ok("Correct request.")
    }.recoverTotal {
      e =>
        BadRequest("Correct request.:")
    }
  }
}

