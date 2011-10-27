package net.ueckerman.spec.comparison

import cuke4duke.annotation.I18n.EN.{When, Given}

class OrderDataManagementSteps(val browserSteps: BrowserSteps) {

  @When(".*user adds an order")
  def orderIsAdded() {
    browserSteps.navigateTo("http://localhost:9000/order")
  }

  @Given(".*user adds many orders")
  def ordersAreAdded() {
    for (i <- 1 to 3) { orderIsAdded() }
  }

}
