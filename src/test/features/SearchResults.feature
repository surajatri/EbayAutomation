@Regression
Feature: Search Results. Where one can sort, filter and see details of resulting products.

  Background:
    Given I navigate to Ebay home page

  @Scenario001
  Scenario Outline: : Search and verify results
    Given I am a non-registered customer
    When I search for an item "<Searched Item>"
    Then I get a list of matching results
    And the resulting items cards show: postage price, No of bids, price or show BuyItNow tag
    Then I can sort the results by Lowest Price
    And the results are listed in the page in "<Sorting Order1>" first order
    Then I can sort the results by Highest Price
    And the results are listed in the page in "<Sorting Order2>" first order
    Then I can filter the results by 'Buy it now'
    And all the results shown in the page have the 'Buy it now' tag

    Examples:
      | Searched Item | Sorting Order1 | Sorting Order2 |
      | Fossil Watch  | Lowest         | Highest        |

  @Scenario002
  Scenario Outline: Search per category
    Given I am a non-registered customer
    When I enter "<Searched Item>" and select Category "<Brand>"
    Then I get a list of matching results
    And I can verify that the results shown as per the the "<Brand>" category
    Examples:
      | Searched Item | Brand   |
      | Mobile Phones | Samsung |

  @Scenario003
  Scenario Outline: Search and navigate through results pages
    Given I am a non-registered customer
    When I search for an item "<Searched Item>"
    Then I get a list of matching results
    And the results show more than one page
    Then the user can navigate through the pages to continue looking at the items
    Examples:
      | Searched Item |
      | Fossil Watch  |