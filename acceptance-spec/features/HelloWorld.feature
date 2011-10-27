Feature: Test of Cuke4Dukes
  In order verify cuke4dukes is installed and configured
  As a developer
  I would like the scenarios of this test feature to execute

Scenario: A simple passing scenario
  Given I intend on saying hello
  And I intend on saying world
  When I say what I intended
  Then I said 'hello world'

  Scenario: A simple failing scenario
  Given I intend on saying some text
  When I say what I intended
  Then I said 'something else'
