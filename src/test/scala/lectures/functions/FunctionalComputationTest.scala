package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class FunctionalComputationTest extends FlatSpec with Matchers {

  "FunctionalComputation" should "return an Array of two elements: Карла and Клара for passed strings " in {
    FunctionalComputation.functionalComputation("Клара у Карла украла корралы, Карл у Клары украл кларнет")("Клара Цеткин обожала Карла Маркса".split(" ")) should be (Array("Клара", "Карла"))
  }

}
