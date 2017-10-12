package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class CurriedComputationTest extends FlatSpec with Matchers {

  "Computation result for passed strings" should "be an Array of two elements: Карла and Клара " in {
    CurriedComputation.curriedComputation("Клара у Карла украла корралы, Карл у Клары украл кларнет")("Клара Цеткин обожала Карла Маркса".split(" ")) should be (Array("Клара", "Карла"))
  }

}
