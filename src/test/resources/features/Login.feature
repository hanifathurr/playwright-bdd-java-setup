Feature: Login feature
  Scenario: User logs in with valid credentials
    Given User accesses the Login page
    When User enters username "standard_user" and password "password"
    Then User redirected to the Home page