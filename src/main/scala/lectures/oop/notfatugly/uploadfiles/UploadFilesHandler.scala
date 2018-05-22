package lectures.oop.notfatugly.uploadfiles

import lectures.oop.notfatugly.AsyncHttpRequestHandler
import lectures.oop.notfatugly.Domain.{File, HttpRequest, HttpResponse}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class UploadFilesHandler(uploadFilesService: UploadFilesService) extends AsyncHttpRequestHandler {
  def handle(request: HttpRequest) = request.body match {
    case Some(requestBody) if requestBody.length > 8388608 =>
      Future.successful(HttpResponse(400, "File size should not be more than 8 MB"))
    case Some(requestBody) =>
      val files = parseFiles(requestBody)
      if (uploadFilesService.uploadingAvailable(files))
        uploadFilesService.upload(files).map(rs => HttpResponse(200, s"Response:\n$rs"))
      else
        Future.successful(HttpResponse(400, "Request contains forbidden extension"))
    case None =>
      Future.successful(HttpResponse(400, "Can not upload empty file"))
  }

  private def parseFiles(requestBody: Array[Byte]): Array[File] = {
    val stringBody = new String(requestBody.filter(_ != '\r'))
    val delimiter = stringBody.takeWhile(_ != '\n')
    stringBody.split(delimiter).drop(1).map { file =>
      file.trim.split("\n") match {
        case Array(name, body) => File(name, body, name.split("\\.")(1))
      }
    }
  }
}