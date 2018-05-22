package lectures.oop.notfatugly

import lectures.oop.notfatugly.Domain.{HttpRequest, HttpResponse}

import scala.concurrent.Future

trait AsyncHttpRequestHandler {
  def handle(request: HttpRequest): Future[HttpResponse]
}