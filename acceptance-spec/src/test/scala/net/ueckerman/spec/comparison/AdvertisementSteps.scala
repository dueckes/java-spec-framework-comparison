package net.ueckerman.spec.comparison

import cuke4duke.annotation.I18n.EN.Given
import cuke4duke.Table

class AdvertisementSteps {

  @Given(".*places and pays for an ad with the following data:$")
  def placeAndPayForAd(table: Table) {
  }

  @Given(".*places an.* ad without a running subscription$")
  def placeAdWithoutARunningSubscription() {
  }

}