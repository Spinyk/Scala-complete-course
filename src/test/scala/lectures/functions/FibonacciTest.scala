package lectures.functions

import org.scalatest.{FlatSpec, Matchers}

class FibonacciTest extends FlatSpec with Matchers {

  "Fibonacci number" should "be equals to 1 for 1 and 2" in {
    Fibonacci.fibs(1) should be(1)
    Fibonacci.fibs(2) should be(1)
  }

  it should "be equals to 987 for 16" in {
    Fibonacci.fibs(16) should be(987)
  }

  it should "throw ArrayIndexOutOfBoundException if passed a negative value" in {
    an[StackOverflowError] should be thrownBy {
      Fibonacci.fibs(-3)
    }
  }

  it should "throw ArrayIndexOutOfBoundException if passed a zero value" in {
    an[StackOverflowError] should be thrownBy {
      Fibonacci.fibs(0)
    }
  }

}
