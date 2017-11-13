package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class SQLAPITest extends FlatSpec with Matchers {

  "SQLAPI" should "return \"SQL has been executed. Congrats!\" for passed query" in {
    val sqlapi = new SQLAPI("jdbc:mysql://localhost/mydatabase")
    sqlapi.execute("select * from table") should be("SQL has been executed. Congrats!")
  }
}
