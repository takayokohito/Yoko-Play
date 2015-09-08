package controllers

import models.Person
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

class PersonApplication extends Controller {

  val registeredPersonMap = Map(
    28 -> "山田 太郎",
    32 -> "山田 次郎",
    45 -> "山田 三郎"
  )
  val errorItemMap = Map(
    "/age" -> "年齢",
    "/name" -> "氏名",
    "/name/firstName" -> "姓",
    "/name/lastName" -> "名"
  )

  def inputPersonInfo = Action.async(parse.json) { request =>
    request.body.validate[Person].map {
      p =>
        val ret: Future[Boolean] = for {
          obj <- checkSameNameExists(p)
        } yield obj

        ret.map {
          res =>
            if(res)
              Ok("同姓同名の方の登録があります.")
            else
              Ok("ようこそ!"+ p.getFullName +"さん!")
        }
    }.recoverTotal {
      e =>
        val errerMsg = e.errors.map {
          error =>
            errorItemMap(error._1.toString()) + "の入力が正しくありません."
        }.mkString(",")
        Future(BadRequest(errerMsg))
    }
  }

  def checkSameNameExists(person: Person): Future[Boolean] = {
    Future.successful(!registeredPersonMap.filter(p => p._2 == person.getFullName).isEmpty)
  }
}

