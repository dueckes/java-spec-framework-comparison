Feature: Test of Cuke4Dukes
  In order verify cuke4dukes is installed and configured
  As a developer
  I would like the scenarios of this test feature to execute

Scenario: A simple passing scenario
  Given the text hello
  And the text world
  When I combine the text
  Then the combined text reads 'hello world'

  Scenario: A simple failing scenario
  Given the text some text
  When I combine the text
  Then the combined text reads 'does not match'
