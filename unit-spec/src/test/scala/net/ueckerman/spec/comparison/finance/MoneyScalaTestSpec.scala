package net.ueckerman.spec.comparison.finance

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.{OneInstancePerTest, Spec}

@RunWith(classOf[JUnitRunner])
class MoneyScalaTestSpec extends Spec with ShouldMatchers with OneInstancePerTest {

  val money = new Money(80)

  describe("add") {

    it("should return Money whose value is that of the Monies combined") {
      val addResult = money.add(new Money(70))

      addResult.getValueInCents should equal(150)
    }

  }

  describe("hashCode") {

    it("should return the same value when called twice") {
      money.hashCode should equal(money.hashCode)
    }

    it("should return the same value when compared with an object of the same type with the same value") {
      val otherMoney = new Money(80)
      money.hashCode should equal(otherMoney.hashCode)
    }

    it("should return a different value when compared with an object of the same type with different value") {
      val otherMoney = new Money(81)
      money.hashCode should not equal(otherMoney.hashCode)
    }

    it("should return a different value when compared with an object of a different type with the same value") {
      val similarToMoney = new SimilarToMoney(80)
      money.hashCode should not equal(similarToMoney.hashCode)
    }

  }

  describe("equals") {

    it("should return true when objects are same") {
      money.equals(money) should be(true)
    }

    it("should return true when objects are the same type with equal values") {
      money.equals (new Money(80)) should be(true)
    }

    it("should return false when objects are not the same type with the same value") {
      money.equals(new SimilarToMoney(80)) should be(false)
    }

    it("should return fale when provided null") {
      money.equals(null) should be(false)
    }

  }

  class SimilarToMoney(val valueInCents: Int) {

    override def hashCode() = new HashCodeBuilder(17, 7).append(valueInCents).build

    override def equals(other: Any): Boolean = {
      if (other == null || !getClass.equals(other.asInstanceOf[AnyRef].getClass)) {
        return false
      }
      val otherSimilarToMoney = other.asInstanceOf[SimilarToMoney]
      this == other || new EqualsBuilder().append(valueInCents, otherSimilarToMoney.valueInCents).build
    }

  }

}
