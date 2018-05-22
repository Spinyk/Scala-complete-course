package lectures.oop.notfatugly.mailing

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AsyncMailClient {
  def send(email: String, subject: String, body: String): Future[Unit] = Future {
    LocalMailer.send(email, subject, body)
  }
}