package net.ueckerman.test.comparison.order

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{OneInstancePerTest, Spec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class IllegalOrderStatusTransitionExceptionScalaTestSpec extends Spec with ShouldMatchers with OneInstancePerTest {

  val illegalOrderStatusTransitionException = new IllegalOrderStatusTransitionException(OrderStatus.NEW, OrderStatus.PAID)

  describe("getMessage") {

    it("should return a string containing the initial status") {
      illegalOrderStatusTransitionException.getMessage should include("NEW")
    }

    it("should return a string containing the target status") {
      illegalOrderStatusTransitionException.getMessage should include("PAID")
    }

  }

}
