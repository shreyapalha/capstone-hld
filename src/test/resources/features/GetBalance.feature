Feature: capstone | Get Balance

  Scenario Outline: <scenarioDescription>
    Given We have request account id '<accountId>' and following data in the database:
      | accountId | bankId | accountBalance | lienAmount | limitAmount |
      |  12       |   1    |  50000         | 1000       |  4000       |
      |  11       |   1    |  30000         | 2000       |  3000       |
    When For Fetch we call the "/account/enquire" with the request params
    Then For Fetch we get response with a httpStatusCode of '<statusCode>'
    And For we get a Validation Exception with message '<message>' and errorCode '<errorCode>'
    And For Fetch we get a Wrong Data Type Exception with message '<message>' and errorCode '<errorCode>'

    Examples:
      |scenarioDescription                             | accountId | statusCode | errorCode | message                    |
      |Getting a account using accountId               |  12       |  200       |  NA       |  NA                        |
      |Fetching a balance but id is not long           |  12       |  400       |  ER005    |  Id should be of long type |
      |Fetching a balance but id doesn't exist         |  13       |  400       |  ER006    |  Id doesn't exist          |
