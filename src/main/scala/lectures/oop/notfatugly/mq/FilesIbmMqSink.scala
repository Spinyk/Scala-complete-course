package lectures.oop.notfatugly.mq

import lectures.oop.notfatugly.Domain.{File, MqConnection}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FilesIbmMqSink extends IbmMqSink[File] {
  import lectures.oop.notfatugly.mq.IbmMq._

  def send(t: File)(implicit mqConnection: MqConnection): Future[String] = Future {
    sendMessageToIbmMq(mqConnection.connection, s"""<Event name="FileUpload"><Origin>SCALA_FTK_TASK</Origin><FileName>${t.name}</FileName></Event>""")
  }
}