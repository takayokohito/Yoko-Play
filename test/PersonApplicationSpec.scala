import play.api.mvc.Results
import play.api.test.{FakeHeaders, FakeRequest, PlaySpecification, WithApplication}


class PersonApplicationSpec extends PlaySpecification with Results {

  "PersonApplication#inputPersonInfo" should {
    // 正常系のテスト
    "response OK." in new WithApplication {
      val request = """{"age":24,"name":{"firstName":"山田","lastName":"太郎"},"bloodType":"O","favoriteNumbers":{"favoriteNumber":1},{"favoriteNumber":2}}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      //status(result) mustEqual OK
      contentAsString(result) mustEqual (request)
    }

    // 異常系のテスト(単数)
    "response NG when request bad 1 param." in new WithApplication {
      val request = """{"age":24,"name":{"firstNamexx":"山田","lastName":"太郎"}}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual ("""{"obj.name.firstName":[{"msg":["error.path.missing"],"args":[]}]}""")
    }

    // 異常系のテスト(複数)
    "response NG when request bad 2 params." in new WithApplication {
      val request = """{"agexx":24,"name":{"firstNamexx":"山田","lastName":"太郎"}}"""
      val Some(result) = route(
        FakeRequest(
          "POST",
          "/sample/person",
          FakeHeaders(Seq("Content-type" -> "application/json")),
          request
        )
      )
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual ("""{"obj.age":[{"msg":["error.path.missing"],"args":[]}],"obj.name.firstName":[{"msg":["error.path.missing"],"args":[]}]}""")
    }
  }

}
