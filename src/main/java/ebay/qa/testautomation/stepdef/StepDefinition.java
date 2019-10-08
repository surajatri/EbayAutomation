package ebay.qa.testautomation.stepdef;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ebay.qa.testautomation.pages.SearchPage;

public class StepDefinition extends SearchPage {

    @Given("^I am a non-registered customer$")
    public void iAmANonRegisteredCustomer() {
        verifyNonRegisterUser();
    }

    @When("^I search for an item \"([^\"]*)\"$")
    public void iSearchForAnItem(String searchedItem) {
        searchItem(searchedItem);
    }

    @Then("^I get a list of matching results$")
    public void iGetAListOfMatchingResults() {
        validateSearchListExists();
    }

    @And("^the resulting items cards show: postage price, No of bids, price or show BuyItNow tag$")
    public void theResultingItemsCardsShowPostagePriceNoOfBidsPriceOrShowBuyItNowTag() {
        validatePriceBidAndBuyNowTag();
    }

    @Then("^I can sort the results by Lowest Price$")
    public void iCanSortTheResultsByLowestPrice() {
        click(sortOptionLink);
        click(lowestPriceOption);

    }

    @And("^the results are listed in the page in \"([^\"]*)\" first order$")
    public void theResultsAreListedInThePageInFirstOrder(String sortOrder) {
        validateResultSortedInOrder(sortOrder);
    }

    @Then("^I can sort the results by Highest Price$")
    public void iCanSortTheResultsByHighestPrice() {
        click(sortOptionLink);
        click(highestPriceOption);
    }

    @Then("^I can filter the results by 'Buy it now'$")
    public void iCanFilterTheResultsByBuyItNow() {
        click(buyItNowFilterLink);
    }

    @And("^all the results shown in the page have the 'Buy it now' tag$")
    public void allTheResultsShownInThePageHaveTheBuyItNowTag() {
        validateAllResultHasBuyItNowTag();
    }

    @And("^the results show more than one page$")
    public void theResultsShowMoreThanOnePage() {
        validateResultReturnMoreThanOnePage();
    }

    @Then("^the user can navigate through the pages to continue looking at the items$")
    public void theUserCanNavigateThroughThePagesToContinueLookingAtTheItems() {
        validateUserNavigateNextPageForOtherProducts();
    }


    @When("^I enter \"([^\"]*)\" and select Category \"([^\"]*)\"$")
    public void iEnterAndSelectCategory(String searchText, String brandName) throws Throwable {
        searchItem(searchText);
        selectBrand(brandName);
    }

    @Given("^I navigate to Ebay home page$")
    public void iNavigateToEbayHomePage() {
        launch();
    }

    @And("^I can verify that the results shown as per the the \"([^\"]*)\" category$")
    public void iCanVerifyThatTheResultsShownAsPerTheTheCategory(String categoryName) {
            validateResultShownAsPerSearchCategory(categoryName);
    }
}


