Feature: Test

  Scenario: client makes call to get category
    When the client calls /api/get
    Then the client will get get status code of 200
