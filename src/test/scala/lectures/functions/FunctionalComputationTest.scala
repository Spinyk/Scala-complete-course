package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class FunctionalComputationTest extends FlatSpec with Matchers {

  "Computation result for passed strings" should "be an Array of two elements: Карла and Клара " in {
    FunctionalComputation.functionalComputation("Клара у Карла украла корралы, Карл у Клары украл кларнет")("Клара Цеткин обожала Карла Маркса".split(" ")) should be (Array("Клара", "Карла"))
  }

}
