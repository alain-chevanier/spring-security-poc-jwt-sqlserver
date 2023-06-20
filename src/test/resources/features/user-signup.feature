Feature: endpoint to perform user signup
  Scenario: Call /signup with valid data and user is created
    Given a valid signup request
    When a request is made to /signup
    Then the request succeeds
      And the user is registered
  Scenario: Call /signup with valid data and but user already exists
    Given a signup request with a username from an existing user
    When a request is made to /signup
    Then the request fails with conflict
      And a "user already exists" message is returned
  Scenario: Call /signup with invalid request data
    Given a signup request with empty values
    When a request is made to /signup
    Then the request fails with bad request