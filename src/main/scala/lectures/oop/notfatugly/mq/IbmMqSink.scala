package lectures.oop.notfatugly.mq

import lectures.oop.notfatugly.Domain.MqConnection

import scala.concurrent.Future

trait IbmMqSink[T] {
  def send(t: T)(implicit mqConnection: MqConnection): Future[String]
}

