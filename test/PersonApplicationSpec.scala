import play.api.mvc.Results
import play.api.test.{FakeHeaders, FakeRequest, PlaySpecification, WithApplication}


class PersonApplicationSpec extends PlaySpecification with Results {

  "PersonApplication#inputPersonInfo" should {
    "response OK." in new WithApplication {
      val request = """{"age":24,"name":{"firstName":"山田","lastName":"太郎"},"bloodType":"O","favoriteNumbers":[1,2,3]}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual OK
      contentAsString(result) mustEqual ("入力内容にエラーはありません。")
    }

    "response OK. when bloodType is empty" in new WithApplication {
      val request = """{"age":24,"name":{"firstName":"山田","lastName":"太郎"},"favoriteNumbers":[1,2,3]}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual OK
      contentAsString(result) mustEqual ("入力内容にエラーはありません。")
    }

    "response NG when request bad 1 param." in new WithApplication {
      val request = """{"age":24,"name":{"firstNamexx":"山田","lastName":"太郎"},"bloodType":"O","favoriteNumbers":[1,2,3]}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual ("""姓の入力が正しくありません.""")
    }

    "response NG when request bad 2 params." in new WithApplication {
      val request = """{"agexx":24,"name":{"firstNamexx":"山田","lastName":"太郎"},"bloodType":"O","favoriteNumbers":[1,2,3]}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual ("""年齢の入力が正しくありません.,姓の入力が正しくありません.""")
    }
  }

}
