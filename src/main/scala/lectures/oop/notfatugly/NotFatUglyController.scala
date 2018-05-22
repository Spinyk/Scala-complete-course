package lectures.oop.notfatugly

import java.security.MessageDigest

import lectures.oop.notfatugly.Domain.{DbConnection, HttpRequest, HttpResponse, MqConnection}
import lectures.oop.notfatugly.database.{Database, FilesRepository, FilesRepositoryImpl}
import lectures.oop.notfatugly.mailing.AsyncMailClient
import lectures.oop.notfatugly.mq.{FilesIbmMqSink, IbmMq, IbmMqSink}
import lectures.oop.notfatugly.uploadfiles.{UploadFilesHandler, UploadFilesService}

import scala.concurrent.Future


/**
  * Данный класс содержит код, наспех написанный одним джуниор-разработчиком,
  * который плохо слушал лекции по эффективному программированию.
  *
  * Вам необходимо:
  * - отрефакторить данный класс, выделив уровни ответственности, необходимые
  *   интерфейсы и абстракции
  * - дописать тесты в FatUglyControllerTest и реализовать в них проверку на
  *   сохранение в БД, отправку сообщения в очередь и отправку email-а
  * - исправить очевидные костыли в коде
  *
  * Код внутри методов, помеченный как DO NOT TOUCH, трогать нельзя (сами методы
  * при этом можно выносить куда и как угодно)
  *
  * Интерфейс метода processRoute менять можно и нужно!
  * Передаваемые данные при этом должны оставаться неизменными.
  *
  * Удачи!
  */
class NotFatUglyController(mailer: AsyncMailClient, ibmMq: FilesIbmMqSink, repository: FilesRepository) {
  private val uploadFilesService = new UploadFilesService(repository, ibmMq, mailer)(MqConnection(IbmMq.connectToIbmMq()) ,DbConnection(Database.connectToPostgresDatabase()))
  private val uploadFilesHandler = new UploadFilesHandler(uploadFilesService)

  def processRoute(request: HttpRequest): Future[HttpResponse] =
    request.route match {
      case "/api/v1/uploadFile" => uploadFilesHandler.handle(request)
      case _ => Future.successful(HttpResponse(404, "Route not found"))
    }
}




