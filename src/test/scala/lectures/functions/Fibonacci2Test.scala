package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class Fibonacci2Test extends FlatSpec with Matchers {

  "Fibonacci2" should "return a number equals to 1 for 1" in {
    Fibonacci2.fibs2(1) should be(1)
  }

  it should "return a number equals to 1 for 2" in {
    Fibonacci2.fibs2(2) should be(1)
  }

  it should "be equals to 987 for 16" in {
    Fibonacci2.fibs2(16) should be(987)
  }

  it should "throw ArrayIndexOutOfBoundException if passed a negative value" in {
    an[ArrayIndexOutOfBoundsException] should be thrownBy {
      Fibonacci2.fibs2(-3)
    }
  }

  it should "throw ArrayIndexOutOfBoundException if passed a zero value" in {
    an[ArrayIndexOutOfBoundsException] should be thrownBy {
      Fibonacci2.fibs2(0)
    }
  }

}
