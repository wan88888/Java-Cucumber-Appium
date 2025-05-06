Feature: SauceLabs Sample App Login
  As a user of the SauceLabs Sample App
  I want to be able to login with valid credentials
  So that I can access the application features

  @successful
  Scenario: Successful login with valid credentials
    Given I am on the login screen
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I tap on the login button
    Then I should see the products page

  Scenario Outline: Unsuccessful login with invalid credentials
    Given I am on the login screen
    When I enter username "<username>"
    And I enter password "<password>"
    And I tap on the login button
    Then I should see the error message "<error_message>"

    Examples:
      | username        | password      | error_message                                                           |
      | locked_out_user | secret_sauce  | Sorry, this user has been locked out.                                   |
      | standard_user   | wrong_password| Username and password do not match any user in this service.            |
      |                 | secret_sauce  | Username is required                                                    |
      | standard_user   |               | Password is required                                                    | 