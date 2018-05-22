package lectures.oop.notfatugly.database

import lectures.oop
import lectures.oop.notfatugly.Domain.{DbConnection, File}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FilesRepositoryImpl extends FilesRepository {
  import oop.notfatugly.database.Database._

  def insert(file: File, id: String)(implicit dbConnection: DbConnection) = Future {
    executePostgresQuery(dbConnection.connection, s"insert into files (id, name, created_on) values ('$id', '${file.name}', current_timestamp)")
  }
}
