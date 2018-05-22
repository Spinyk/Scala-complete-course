package lectures.oop.notfatugly

import lectures.oop.notfatugly.Domain.{HttpRequest, HttpResponse}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class NotFatUglyControllerTest extends FlatSpec with Matchers {

  behavior of "NotFatUglyController"

  it should "successfully process single file" in {
    val requestBody =
      """DELIMITER
        |file1.txt
        |This is body of file1
      """.stripMargin
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    val expected = "Response:\n- saved file file1.txt to 063f83f94e59aac2edd719fab1d179f86084887a.txt (file size: 21)"

    response.code shouldBe 200
    response.body shouldBe expected
  }

  it should "successfully process two files" in {
    val requestBody =
      """DELIMITER22
        |file1.txt
        |This is body of file1
        |DELIMITER22
        |file2.txt
        |This is body of file2!!
      """.stripMargin
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    val expected = "Response:\n- saved file file1.txt to 063f83f94e59aac2edd719fab1d179f86084887a.txt (file size: 21)\n- saved file file2.txt to 7387fa41a69d93b59b67bd46ab18a72c81edb767.txt (file size: 23)"

    response.code shouldBe 200
    response.body shouldBe expected

  }

  it should "return 404 for unknown route" in {
    val response = Await.result(controller.processRoute(HttpRequest("/api", None)), 5.second)

    response.code shouldBe 404
    response.body shouldBe "Route not found"
  }

  it should "return 400 for empty body" in {
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", None)), 5.second)

    response.code shouldBe 400
    response.body shouldBe "Can not upload empty file"
  }

  it should "return 400 for forbidden extension" in {
    val requestBody =
      """DELIMITER
        |file1.exe
        |This is body of file1
      """.stripMargin

    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    response.code shouldBe 400
    response.body shouldBe "Request contains forbidden extension"
  }

  it should "return 400 for file greater than 8 MB" in {
    val requestBody =
      """DELIMITER
        |file1.exe
        |This is body of file1
      """.stripMargin + "ololololol" * (1024 * 1024)

    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    response.code shouldBe 400
    response.body shouldBe "File size should not be more than 8 MB"
  }

  private val controller = new NotFatUglyController()

}
