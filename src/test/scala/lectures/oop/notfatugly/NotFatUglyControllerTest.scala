package lectures.oop.notfatugly

import lectures.oop.notfatugly.Domain.{DbConnection, HttpRequest, HttpResponse, MqConnection}
import lectures.oop.notfatugly.database.{Database, FilesRepositoryImpl}
import lectures.oop.notfatugly.mailing.AsyncMailClient
import lectures.oop.notfatugly.mq.{FilesIbmMqSink, IbmMq}
import lectures.oop.notfatugly.uploadfiles.{UploadFilesHandler, UploadFilesService}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers._

class NotFatUglyControllerTest extends FlatSpec with Matchers with MockitoSugar {

  trait mocks {
    val mailer = new AsyncMailClient()
    val ibmMq = new FilesIbmMqSink()
    val repository = new FilesRepositoryImpl()

    val controller = new NotFatUglyController(mailer, ibmMq, repository)
  }

  trait mocksForVerify {
    val mailer = mock[AsyncMailClient]
    val ibmMq = mock[FilesIbmMqSink]
    val repository = mock[FilesRepositoryImpl]

    when(repository.insert(any(), any())(any())).thenReturn(Future.successful("inseretedData"))
    when(ibmMq.send(any())(any())).thenReturn(Future.successful("sentData"))
    when(mailer.send(any(), any(), any())).thenReturn(Future.unit)


    val controller = new NotFatUglyController(mailer, ibmMq, repository)
  }

  behavior of "NotFatUglyController"

  it should "successfully process single file" in new mocks {
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

  it should "successfully process two files" in new mocks {
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

  it should "return 404 for unknown route" in new mocks {
    val response = Await.result(controller.processRoute(HttpRequest("/api", None)), 5.second)

    response.code shouldBe 404
    response.body shouldBe "Route not found"
  }

  it should "return 400 for empty body" in new mocks {
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", None)), 5.second)

    response.code shouldBe 400
    response.body shouldBe "Can not upload empty file"
  }

  it should "return 400 for forbidden extension" in new mocks {
    val requestBody =
      """DELIMITER
        |file1.exe
        |This is body of file1
      """.stripMargin

    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    response.code shouldBe 400
    response.body shouldBe "Request contains forbidden extension"
  }

  it should "return 400 for file greater than 8 MB" in new mocks {
    val requestBody =
      """DELIMITER
        |file1.exe
        |This is body of file1
      """.stripMargin + "ololololol" * (1024 * 1024)

    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    response.code shouldBe 400
    response.body shouldBe "File size should not be more than 8 MB"
  }

  it should "interact with db" in new mocksForVerify {
    val requestBody =
      """DELIMITER
        |file1.txt
        |This is body of file1
      """.stripMargin
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    verify(repository).insert(any(), any())(any())
  }

  it should "interact with mq" in new mocksForVerify {
    val requestBody =
      """DELIMITER
        |file1.txt
        |This is body of file1
      """.stripMargin
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    verify(ibmMq).send(any())(any())
  }

  it should "interact with mailer" in new mocksForVerify {
    val requestBody =
      """DELIMITER
        |file1.txt
        |This is body of file1
      """.stripMargin
    val response = Await.result(controller.processRoute(HttpRequest("/api/v1/uploadFile", Some(requestBody.getBytes))), 5.second)

    verify(mailer).send(any(), any(), any())
  }
}
