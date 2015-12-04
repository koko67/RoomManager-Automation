@Login
Feature: Login
  Test successfully and unsuccessfully login and logout
  Scenario: Users should be able to login using valid credentials
    Given I log in successfully as "test" with password "Client123"
    Then I should log in successfully