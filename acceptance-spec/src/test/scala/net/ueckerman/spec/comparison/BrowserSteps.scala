package net.ueckerman.spec.comparison

class BrowserSteps {

  def navigateTo(url: String) {
    Browser.driver.get(url)
  }

}
