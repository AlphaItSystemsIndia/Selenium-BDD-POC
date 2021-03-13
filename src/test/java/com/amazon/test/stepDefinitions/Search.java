package com.amazon.test.stepDefinitions;

import com.amazon.test.web.ProductSearch;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class Search extends TestCase {
    private static final Logger logger = Logger.getLogger(Search.class.getCanonicalName());

    /**
     * This step definition sets the given keyword in the product search box by calling {@link ProductSearch#setKeyword(String)} method.
     *
     * @param searchKeyword search keyword
     */
    @And("^user enters (.*) in search box$")
    public void userEntersKeyword(String searchKeyword) {
        logger.debug("Step definition - user enters " + searchKeyword + " in search box");
        try {
            ProductSearch.setKeyword(searchKeyword);
            logger.debug("STEP OK");
        } catch (NoSuchElementException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        }
    }

    /**
     * This step definition sets the category name in the product search box by calling {@link ProductSearch#selectCategory(String)} method.
     *
     * @param categoryName name of category
     */
    @And("^user selects (.*) category in search box$")
    public void userSelectsCategory(String categoryName) {
        logger.debug("Step definition - user selects " + categoryName + " category in search box");
        try {
            ProductSearch.selectCategory(categoryName);
            logger.debug("STEP OK");
        } catch (NoSuchElementException | UnexpectedTagNameException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        }
    }

    /**
     * This step definition submits the product search by invoking {@link ProductSearch#submit()} method.
     */
    @When("^user submits search box$")
    public void userSubmitsSearch() {
        logger.debug("Step definition - user submits search box");
        try {
            assertTrue("Failed to submit product search", ProductSearch.submit());
            logger.debug("STEP OK");
        } catch (NoSuchElementException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition asserts that page displays the search results by invoking {@link ProductSearch#hasResults()} method.
     */
    @Then("^search results are displayed$")
    public void searchResultsDisplayed() {
        logger.debug("Step definition - search results are displayed");
        try {
            assertTrue("Expected - Search results displayed, Actual - No search results displayed", ProductSearch.hasResults());
            logger.debug("STEP OK");
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }
}
