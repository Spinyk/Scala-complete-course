package lectures.oop.notfatugly.database

import lectures.oop.notfatugly.Domain.{DbConnection, File}

import scala.concurrent.Future

trait FilesRepository {
  def insert(file: File, id: String)(implicit dbConnection: DbConnection): Future[String]
}
