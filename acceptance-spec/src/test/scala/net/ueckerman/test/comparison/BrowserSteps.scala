package net.ueckerman.test.comparison

class BrowserSteps {

  def navigateTo(url: String) {
    Browser.driver.get(url)
  }

}
