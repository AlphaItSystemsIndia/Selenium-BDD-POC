package com.amazon.test.stepDefinitions;

import com.amazon.test.ProjectProperties;
import com.amazon.test.Selenium;
import com.amazon.test.context.ContextItem;
import com.amazon.test.context.ScenarioContext;
import com.amazon.test.util.Credentials;
import com.amazon.test.util.Field;
import com.amazon.test.web.page.Page;
import com.amazon.test.web.page.PageType;
import com.amazon.test.web.ui.WebButton;
import com.amazon.test.web.ui.WebForm;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import java.net.MalformedURLException;

public class Common extends TestCase {
    private static final Logger logger = Logger.getLogger(Common.class.getCanonicalName());

    @Before
    public void beforeEachScenario() {
        logger.debug("beforeEachScenario - Starting selenium browser and creating scenario context");
        Selenium.startBrowser();
        ScenarioContext.create();
    }

    @After
    public void afterEachScenario() {
        logger.debug("afterEachScenario - Quitting selenium browser and purging scenario context");
        Selenium.quitBrowser();
        ScenarioContext.purge();
    }

    /**
     * This step definition sets the type of the page to visit.
     * The name of page set in the feature file is captured by this
     * step definition method and is used to create {@link PageType} enum by invoking {@link PageType#makeFrom(String)}
     * method and passing page name as argument. {@link PageType} enum is set in the {@link ScenarioContext} using key {@link ContextItem#VISIT_PAGE_TYPE} which is
     * later required by the step definition {@link Common#pageIsLoaded()} for creating {@link Page} corresponding to {@link PageType} enum.
     *
     * @param pageName Name of the page to visit
     */
    @Given("^user visits (.*) page$")
    public void userVisitsPage(String pageName) {
        logger.debug("Step definition - user visits " + pageName + " page");
        try {
            logger.debug("Setting ContextItem " + ContextItem.VISIT_PAGE_TYPE);
            ScenarioContext.setItem(ContextItem.VISIT_PAGE_TYPE, PageType.makeFrom(pageName));
            logger.debug("STEP OK");
        } catch (InvalidArgumentException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        }
    }

    /**
     * This step definition prepares the target {@link Page} and visits it so the page becomes active.
     * Data required to create a {@link Page} is extracted from the {@link ScenarioContext}.
     * Prior to this step definition invocation, it is required to set {@link ContextItem#VISIT_PAGE_TYPE} context item
     * in the {@link ScenarioContext} to a {@link PageType} enum for the determination of the target {@link Page} to load.
     */
    @And("^page is loaded$")
    public void pageIsLoaded() {
        logger.debug("Step definition - page is loaded");
        logger.debug("Getting ContextItem " + ContextItem.VISIT_PAGE_TYPE);
        Object pageTypeItem = ScenarioContext.getItem(ContextItem.VISIT_PAGE_TYPE);
        try {
            assertTrue("Expected - ContextItem " + ContextItem.VISIT_PAGE_TYPE + " , Actual - " + pageTypeItem, pageTypeItem instanceof PageType);
            PageType pageType = (PageType) pageTypeItem;
            Page pageToVisit = Page.make(ProjectProperties.getHomeUrl(), pageType, null);
            pageToVisit.visit();
            logger.debug("STEP OK");
        } catch (MalformedURLException exception) {
            logger.error(exception);
            fail("Cannot make " + Page.class.getSimpleName() + " to visit : " + exception.getMessage());
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition asserts the target {@link WebButton} to be clicked.
     * The target {@link WebButton} is determined by its label text.
     *
     * @param buttonLabel Label of the target {@link WebButton}
     */
    @And("^user clicks (.*) button$")
    public void userClicksButton(String buttonLabel) {
        logger.debug("Step definition - user clicks " + buttonLabel + " button");
        try {
            WebButton btn = new WebButton(buttonLabel);
            boolean clicked = btn.click();
            assertTrue("Expected - clicked button " + buttonLabel + " , Actual - not clicked button " + buttonLabel, clicked);
            logger.debug("STEP OK");
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition asserts the browser redirection to the given page.
     * The redirected is verified by comparing the URLs of the expected and actual {@link Page}s.
     *
     * @param pageName Name of the page browser redirected to
     */
    @Then("^browser redirects to (.*) page$")
    public void browserRedirectsTo(String pageName) {
        logger.debug("Step definition - browser redirects to " + pageName + " page");
        try {
            // Wait for few seconds for redirection to get complete
            Thread.sleep(2000);
            Page expectedPage = Page.make(ProjectProperties.getHomeUrl(), PageType.makeFrom(pageName), null);
            Page currentPage = Page.currentPage();
            assertEquals("Actual redirect base url is different than expected",
                    expectedPage.getBaseUrl(),
                    currentPage.getBaseUrl()
            );
            assertEquals("Actual redirect url path is different than expected",
                    expectedPage.getPathSegment(),
                    currentPage.getPathSegment()
            );
            logger.debug("STEP OK");
        } catch (MalformedURLException | InterruptedException | InvalidArgumentException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition sets a field in the form on the page.
     * The name of the field and the value to set is captured from the feature file by this step definition.
     * To determine the field type as well as its unique id, {@link Field} enum is created for the given field name.
     * Field type is determined by invoking {@link Field#getType()} method and unique id determined by invoking
     * {@link Field#getId()} method. {@link WebForm} class defines methods which are used to set field values.
     *
     * @param fieldName  name of the field
     * @param fieldValue value to set in the field
     */
    @And("^user sets (.*) to value (.*)$")
    public void userFillsFieldWithValue(String fieldName, String fieldValue) {
        logger.debug("Step definition - user sets " + fieldName + " to value " + fieldValue);
        try {
            if (fieldName.equals(Field.EmailPhoneField.getLabel()) ||
                    fieldName.equals(Field.PasswordField.getLabel())) {
                fieldValue = Credentials.decode(ProjectProperties.getProperty(fieldValue));
            }
            WebForm formToFill = new WebForm();
            Field formField = Field.makeFromLabel(fieldName);
            switch (formField.getType()) {
                case TextField:
                    formToFill.setText(formField.getId(), fieldValue);
                    break;
                case SelectField:
                    formToFill.selectOption(formField.getId(), fieldValue);
            }
            logger.debug("STEP OK");
        } catch (InvalidArgumentException | NoSuchElementException | UnexpectedTagNameException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        }
    }
}
