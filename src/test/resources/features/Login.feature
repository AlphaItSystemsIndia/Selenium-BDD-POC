Feature: User logs in to the account using registered credentials

  Scenario Outline: User logs in using email and password
    Given user visits Home page
    And page is loaded
    And user is not signed in
    And user clicks Sign in button
    And sign in page asks for Email or mobile phone number
    When user sets Email or mobile phone number to value <username>
    And user clicks Continue button
    And sign in page asks for Password
    And user sets Password to value <password>
    And user clicks Sign In button
    Then browser redirects to Home page
    And user is signed in
    Examples:
      | username   | password      |
      | USER_EMAIL | USER_PASSWORD |
    # Actual username and password are stored in the encoded form in project.properties file
    # using USER_EMAIL and USER_PASSWORD keys respectively.
