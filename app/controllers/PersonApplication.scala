package controllers

import models.Person
import play.api.libs.json.{Json, JsError}
import play.api.mvc.{Action, Controller}

class PersonApplication extends Controller {

  //val errorMsgMap = Map("error.path.missing"->"のパスが見つかりません.")
  val errorItemMap = Map(
    "/age"->"年齢",
    "/name"->"氏名",
    "/name/firstName"->"姓",
    "/name/lastName"->"名"
  )

  def inputPersonInfo = Action(parse.json) { request =>
    request.body.validate[Person].map {
      p =>
        Ok("入力内容にエラーはありません。")
    }.recoverTotal {
      e =>
        val errerMsg = e.errors.map{
          error =>
            errorItemMap(error._1.toString()) + "の入力が正しくありません."
        }.mkString(",")
        BadRequest(errerMsg)
    }
  }
}

