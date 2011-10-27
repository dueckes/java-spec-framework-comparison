Feature: Running Subscription renewal reminder email
  In order for paid customers to be informed of the charges and lifecycle of their advertisement
  As a customer
  I would like to receive regular emails informing me of pending renewals and charges

  Background:
    Given a user has logged in
    And the users nickname is 'Fred'
    And the user places and pays for an ad with the following data:
    | category                 | title    | running subscription duration |
    | Has running subscription | Freds Ad | 14                            |

  Scenario: Renewal email is sent when renewal date is within 36 hours
    Given no renewal email has been previously sent
    And the advertisements next renewal date is within 36 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder is sent
    And the renewal date is 14 days after the end of the current period
    And the renewal time is XYZ
    And the email contains the following content
    """
Beste Fred,

Uw advertentie :ad_id met de titel Freds Ad zal op :renewal_date om :renewal_time automatisch verlengd worden voor een periode van 1. De kosten voor deze verlenging worden via automatische incasso afgeschreven.

Heeft u uw auto inmiddels verkocht? Vergeet dan niet uw advertentie voor :renewal_date::renewal_time te verwijderen. Mocht uw advertentie op dat moment nog op Marktplaats staan, dan wordt deze automatisch voor u verlengd. Bij het verlengen van de advertentie wordt deze opnieuw omhoog geplaatst.

U kunt uw advertentie eenvoudig beheren door in te loggen op Marktplaats:

    * Bekijk of wijzig uw advertentie: ...
    * Verwijder uw advertentie: ...

Wij wensen u veel succes met adverteren op Marktplaats.

Met vriendelijke groet,

Het Marktplaats team
www.marktplaats.nl

Heeft u vragen over het gebruik van Marktplaats? Bekijk de meest gestelde vragen of neem contact op met ons.
    """

  Scenario: Renewal email is sent when renewal date is within 24 hours
    Given no renewal email has been previously sent
    And the advertisements next renewal date is within 24 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder is sent for renewal period 1

  Scenario: Renewal email is sent when ad is being renewed for second renewal period and date is within 36 hours
    Given an email has been sent for renewal period 1
    And no email has been sent for renewal period 2
    And the advertisements next renewal date is within 36 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder is sent for renewal period 2

  Scenario: Renewal email is sent when ad is being renewed for third renewal period and date is within 36 hours
    Given an email has been sent for renewal periods 1 and 2
    And no email has been sent for renewal period 3
    And the advertisements next renewal date is within 36 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder is sent for renewal period 3

  Scenario: Renewal email is not sent with email is already sent
    Given an email has been sent for renewal period 1
    And the renewal date for renewal period 1 is within 36 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder email is not sent to the user

  Scenario: Renewal email is not sent with renewal date exceeds 36 hours
    Given no renewal email has been previously sent
    And the advertisements next renewal date exceeds 36 hours
    When the running subscription reminder email job runs
    Then a running subscription reminder email is not sent to the user

  Scenario: Renewal email is not sent for ads without a running subscription
    Given the user places another ad without a running subscription
    When the running subscription reminder email job runs
    Then a running subscription reminder email is not sent to the user for the other ad
