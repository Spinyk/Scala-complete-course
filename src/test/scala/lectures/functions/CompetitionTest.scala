package lectures.functions

import lectures.operators.Competition
import org.scalatest.{FlatSpec, Matchers}

class CompetitionTest extends FlatSpec with Matchers{
  "Competition" should "compute finalResult greater than 0" in {
    Competition.finalResult should be > 0
  }
}
