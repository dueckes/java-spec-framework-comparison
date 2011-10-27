package net.ueckerman.test.comparison

import cuke4duke.annotation.I18n.EN.{Then, When, Given}
import org.scalatest.matchers.ShouldMatchers

class RunningSubscriptionSteps extends ShouldMatchers {

  @Given("^no renewal email has been previously sent$")
  def noRenewalEmailHasBeenPreviouslySent() {
  }

  @Given(".*advertisements next renewal date is within (\\d*) hours$")
  def advertisementsNextRenewalDateIsWithinXHours(withinHours: String) {
    renewalDateForARenewalPeriodIsWithinXHours("1", withinHours)
  }

  @Given(".*advertisements next renewal date exceeds (\\d*) hours$")
  def advertisementNextRenewalDateExceedsXHours(exceedsHours: String) {
  }

  @Given(".*renewal date for renewal period (\\d*) is within (\\d*) hours$")
  def renewalDateForARenewalPeriodIsWithinXHours(renewalPeriod: String, withinHours: String) {
  }

  @Given("^an email has been sent for renewal period (\\d*)$")
  def emailHasBeenBeenSentForRenewalPeriod(renewalPeriod: String) {
  }

  @Given(".*email has been sent for renewal periods (\\d*) and (\\d*)$")
  def emailHasBeenBeenSentForRenewalPeriodsXAndY(firstRenewalPeriod: String, secondRenewalPeriod: String) {
    emailHasBeenBeenSentForRenewalPeriod(firstRenewalPeriod)
    emailHasBeenBeenSentForRenewalPeriod(secondRenewalPeriod)
  }

  @Given("^no email has been sent for renewal period (\\d*)$")
  def noEmailHasBeenSentForRenewalPeriodX(renewalPeriod:String) {
  }

  @When(".*running subscription reminder email job runs$")
  def runningSubscriptionReminderEmailJobRuns() {
  }

  @Then(".*running subscription reminder is sent$")
  def runningSubscriptionReminderIsSent() {
  }

  @Then(".*renewal date is (\\d*) days after the end of the current period$")
  def renewalDateIsXDaysAfterTheEndOfTheCurrentPeriod(daysAfterCurrentPeriod: String) {
  }

  @Then("^the renewal time is (.*)$")
  def renewalTimeIs(renewalTime: String) {
  }

  @Then(".*email contains the following content$")
  def emailContainsTheFollowingContent(emailContentTemplate: String) {
  }

  @Then(".*running subscription reminder is sent for renewal period (\\d*)$")
  def runningSubscriptionReminderIsSentForRenewalPeriodX(renewalPeriod: String) {
  }

  @Then(".*running subscription reminder email is not sent.*")
  def runningSubscriptionReminderEmailIsNotSent() {
  }

}
