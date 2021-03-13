Feature: User searches products by search keyword and product category.

  Scenario Outline: User searches products using search box
    Given user visits Home page
    And page is loaded
    And user enters <search keyword> in search box
    And user selects <category> category in search box
    When user submits search box
    Then search results are displayed

    Examples:
      | search keyword | category       |
      | laptop         | All Categories |
      | heater         | Appliances     |
      | keyboard       | Electronics    |
