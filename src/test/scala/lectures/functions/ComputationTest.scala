package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class ComputationTest extends FlatSpec with Matchers {

  "Computation" should "return an Array of two elements: Карла and Клара for passed strings " in {
    Computation.computation("Клара у Карла украла корралы, Карл у Клары украл кларнет", "Клара Цеткин обожала Карла Маркса".split(" ")) should be (Array("Клара", "Карла"))
  }

}
