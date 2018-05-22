package lectures.oop.notfatugly

object Domain {
  final case class HttpResponse(code: Int, body: String)
  final case class HttpRequest(route: String, body: Option[Array[Byte]])

  final case class File(name: String, body: String, extension: String)

  final case class MqConnection(connection: Int)
  final case class DbConnection(connection: Int)
}