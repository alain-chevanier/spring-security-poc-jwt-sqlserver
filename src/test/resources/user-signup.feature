Feature: endpoint to perform user signup
  Scenario: a correct call is made to /signup
    When the client calls /signup with correct information
    Then the client receives status code of 200
    And the user is registered successfully