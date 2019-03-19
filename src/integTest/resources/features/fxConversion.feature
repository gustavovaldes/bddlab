Feature: Fx Conversion Usability


  Scenario: Money Conversion from USD to CLP
    Given I have a valid user
    When I make a conversion with 2 USD and date 2018-02-03
    Then I should receive a valid response
    And I the converted amount should be 1205.5009603581 CLP

  Scenario: Money Conversion from USD to CLP with data from  year 2017
    Given I have a valid user
    When I make a conversion with 25 USD and date 2017-12-03
    Then I the converted amount should be 16182.505197715534 CLP


  Scenario: User without access
    Given I don't have a valid user
    When I make a conversion with 2 USD and date 2017-12-03
    Then I should receive an invalid response


  @NotImplemented
  Scenario: Money Conversion from USD to EUR
    Given I have a valid user
    When I make a conversion with 2 USD to EUR and date 2018-02-03
    Then I should receive a valid response
    And I the converted amount should be 1205.5009603581486 EUR


  @NotImplemented
  Scenario: Money Conversion 3
    Given I have a valid user
    And Today is 2018-02-23
    When I call the conversion service without any additional information
    Then I should receive this information
      | CLP | 23 |
      | CLP | 23 |
      | CLP | 23 |
      | CLP | 23 |
      | CLP | 23 |
      | CLP | 23 |

  @NotImplemented
  Scenario: Requesting data too old
    Given I have a valid user
    And to-do
    When I call the conversion service with 2 USD and data before year 2001
    Then I receive the  message Information is Available only from 2002-01-01



#Scenario: Access with invalid User
#Given I don't have a valid user
#And to-do
#When I call the conversion service with 2 USD...


#Scenario Requesting currencies not supported


#Scenario Requesting amounts too big


#Scenarios Resquesting information in wrong format
