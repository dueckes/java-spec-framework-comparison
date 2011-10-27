package net.ueckerman.test.comparison.order

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{OneInstancePerTest, Spec}

@RunWith(classOf[JUnitRunner])
class OrderStatusScalaTestSpec extends Spec with ShouldMatchers with OneInstancePerTest {

  val newStatus = OrderStatus.NEW
  val paidStatus = OrderStatus.PAID

  describe("isValidPriorStatus") {

    describe("when the prior status is new") {

      it("should return true when attempting to transition to paid") {
        OrderStatus.PAID.isValidPriorStatus(newStatus) should equal(true)
      }

      it("should return true when attempting to transition to rejected") {
        OrderStatus.REJECTED.isValidPriorStatus(newStatus) should be(true)
      }

      it("should return true when attempting to transition to cancelled") {
        OrderStatus.CANCELLED.isValidPriorStatus(newStatus) should be(true)
      }

    }

    describe("when the prior status is paid") {

      it("should return false when attempting to transition to cancelled") {
        OrderStatus.CANCELLED.isValidPriorStatus(paidStatus) should be(false)
      }

      it("should return false when attempting to transition to rejected") {
        OrderStatus.REJECTED.isValidPriorStatus(paidStatus) should be(false)
      }

    }

  }

}
