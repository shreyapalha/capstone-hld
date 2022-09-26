Feature: capstone-hld | SignUp

#  Scenario : without using database
#    Given We have balance amount '<balance_amount>',lien amount '<lien_amount>' and limit balance '<limit_balance>'
#    And We have request data with bankId '<bankId>',username '<username>' password '<password>'
#    When We call the "/api/signUp"
#    Then We got response with status of '<status>'


  Scenario Outline: <scenarioDescription>
    Given We have balance amount '<balance_amount>',lien amount '<lien_amount>' and limit balance '<limit_balance>' with following data
       | balance_amount | lien_amount | limit_balance  | bankId | username | password |
       |   34000        |  2000       | 1000           |  4     |  swaraji | 1234     |

    And We have request data with bankId '<bankId>',username '<username>' password '<password>' with following data
    When We call the "/api/signUp" with success
    Then We got a response with status of '<status>'



  Examples:
     | scenarioDescription          | bankId | username | password | balance_amount | lien_amount | limit_balance | status |
     | adding a user                |  9    | swaraj   | 1234     | 30000          | 4000        |  1000         | 200   |