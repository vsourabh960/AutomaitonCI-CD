
@tag
Feature: purchase the order from ecommerce website
  I want to use this template for my feature file

  Background: 
    Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Postive test of submitting the order
    Given logged in with username <name> and password <password>
    When add product <productName> to the cart
    And checkout <productName> and submit order
    Then "THANKYOU FOR THE ORDER." message is displayed on confirmationpage

    Examples: 
      | name                   | password   | productName |
      | saurabh12342@gmail.com | Rahul@1725 | ZARA COAT 3 |