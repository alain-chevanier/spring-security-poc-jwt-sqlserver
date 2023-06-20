Feature: endpoint to perform user signup
  Scenario: Call /signup with valid data and user is created
    When the client calls /signup with valid information
    Then the client receives status code of 200
    And the user was registered successfully
  Scenario: Call /signup with valid data and but user already exists
    When the client calls /signup with information from an existing user
    Then the client receives status code of 409
    And the user already exists message is received