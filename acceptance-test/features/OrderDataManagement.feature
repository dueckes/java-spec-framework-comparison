Feature: Creation and display of Order information
  In order maintain order information
  As a trivial order application tool user
  I would like to add and view order information

Background:
  Given A user has logged in

Scenario: Order information is added
  When the user adds an order
  Then the new orders details are shown

Scenario: Added orders are shown
  Given the user adds many orders
  When the user navigates to the order overview page
  Then all added orders are be shown
