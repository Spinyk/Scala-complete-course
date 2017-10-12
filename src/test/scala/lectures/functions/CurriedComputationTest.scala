package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class CurriedComputationTest extends FlatSpec with Matchers {

  "CurriedComputation" should "return an Array of two elements: Карла and Клара for passed strings " in {
    CurriedComputation.curriedComputation("Клара у Карла украла корралы, Карл у Клары украл кларнет")("Клара Цеткин обожала Карла Маркса".split(" ")) should be (Array("Клара", "Карла"))
  }

}
