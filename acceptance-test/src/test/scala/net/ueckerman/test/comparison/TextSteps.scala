package net.ueckerman.test.comparison

import collection.mutable.ListBuffer
import cuke4duke.annotation.I18n.EN.{Then, When, Given}
import org.scalatest.matchers.ShouldMatchers

class TextSteps extends ShouldMatchers {

  val texts = ListBuffer[String]()
  var combinedText: String = _

  @Given("the text (.+)$")
  def addText(someText: String) {
    texts += someText
  }

  @When("I combine the text")
  def combineTheText() {
    combinedText = texts.toList.mkString(" ")
  }

  @Then("the combined text reads '([^']*)'")
  def textMustRead(expectedText: String) {
    combinedText should equal(expectedText)
  }

}
