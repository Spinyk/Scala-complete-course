package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class SQLAPITest extends FlatSpec with Matchers {

  "For passed query execution result" should "be \"SQL has been executed. Congrats!\"" in {
    val sqlapi = new SQLAPI("jdbc:mysql://localhost/mydatabase")
    sqlapi.execute("select * from table") should be("SQL has been executed. Congrats!")
  }
}
