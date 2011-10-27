package net.ueckerman.spec.comparison

import collection.mutable.ListBuffer
import cuke4duke.annotation.I18n.EN.{Then, When, Given}
import org.scalatest.matchers.ShouldMatchers

class HelloWorldSteps extends ShouldMatchers {

  val phrasesToBeSaid = ListBuffer[String]()
  var spokenPhrase: String = _

  @Given("I intend on saying (.+)$")
  def intendOnSaying(phrase: String) {
    phrasesToBeSaid += phrase
  }

  @When("I say what I intended")
  def sayWhatIIntended() {
    spokenPhrase = phrasesToBeSaid.toList.mkString(" ")
  }

  @Then("I said '([^']*)'")
  def verifyIHaveSaid(expectedPhrase: String) {
    spokenPhrase should equal(expectedPhrase)
  }

}
