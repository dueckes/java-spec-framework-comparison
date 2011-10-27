package net.ueckerman.spec.comparison

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver

object Browser {

  val driver: WebDriver = new FirefoxDriver()

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() {
      driver.close()
    }
  })

}
