Feature: endpoint to perform user signup
  Scenario: a correct call is made to /signup
    When the client calls /signup with valid information
    Then the client receives status code of 200
    And the user was registered successfully