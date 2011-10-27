package net.ueckerman.test.comparison

import cuke4duke.annotation.I18n.EN.Given

class UserSteps {

  @Given(".*user has logged in$")
  def userLogsIn() {
    // Intentionally does nothing
  }

  @Given(".*users nickname is '([^']*)'$")
  def establishUsersNickname(nickName: String) {
    // Intentionally does nothing
  }

}