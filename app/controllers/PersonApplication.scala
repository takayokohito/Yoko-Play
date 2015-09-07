package controllers

import models.Person
import play.api.libs.json.{Json, JsError}
import play.api.mvc.{Action, Controller}

class PersonApplication extends Controller {

  def inputPersonInfo = Action(parse.json) { request =>
    request.body.validate[Person].map {
      p =>
        Ok(Json.toJson(p))
    }.recoverTotal {
      e =>
        BadRequest(JsError.toJson(e))
    }
  }
}

