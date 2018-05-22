package lectures.oop.notfatugly.uploadfiles

import lectures.oop.notfatugly.Domain.{DbConnection, File, MqConnection}
import lectures.oop.notfatugly.mailing.AsyncMailClient
import lectures.oop.notfatugly.mq.FilesIbmMqSink
import lectures.oop.notfatugly.database.FilesRepository
import lectures.oop.notfatugly.FilesOps.hash

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UploadFilesService(filesRepository: FilesRepository,
                         ibmMqSink: FilesIbmMqSink,
                         localMailer: AsyncMailClient)
                        (implicit val mqConnection: MqConnection, val dbConnection: DbConnection) {
  import UploadFilesService.ForbiddenExtensions

  def upload(files: Array[File]): Future[String] =
    Future.traverse(files.toSeq) { file =>
      val id = hash(s"${file.name}\n${file.body}")
      for {
        _ <- filesRepository.insert(file, id)
        _ <- ibmMqSink.send(file)
        _ <- localMailer.send(
          email = "admin@admin.tinkoff.ru",
          subject = "File has been uploaded",
          body = s"Hey, we have got new file: ${file.name}"
        )
      } yield s"- saved file ${file.name} to $id.${file.extension} (file size: ${file.body.length.toString})\n"
    }.map { uploadResults =>
      //      Дроп райт 1
      uploadResults.foldLeft(new StringBuilder())((sb, result) => sb.append(result)).dropRight(1).toString()
    }

  def uploadingAvailable(files: Array[File]): Boolean =
    files.forall(file => !ForbiddenExtensions.contains(file.extension))

}

object UploadFilesService {
  val ForbiddenExtensions = Set("exe", "bat", "com", "sh")
}
