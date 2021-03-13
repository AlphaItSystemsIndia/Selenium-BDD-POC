package com.amazon.test.stepDefinitions;

import com.amazon.test.ProjectProperties;
import com.amazon.test.web.page.Page;
import com.amazon.test.web.page.PageType;
import io.cucumber.java.en.And;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;

public class Login extends TestCase {
    private static final Logger logger = Logger.getLogger(Login.class.getCanonicalName());

    /**
     * This step definition asserts that user is not currently signed in by checking the presence of 'sign in' text
     * in the content of current page. The 'sign in' text is only present in the page content when user is not signed in.
     */
    @And("^user is not signed in$")
    public void userNotSignedIn() {
        logger.debug("Step definition - user is not signed in");
        try {
            Page currentPage = Page.currentPage();
            assertTrue("Expected - page contains 'sign in' text, Actual - page does not contain 'sign in' text", currentPage.contentContainsIgnoreCase("sign in"));
            logger.debug("STEP OK");
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition asserts that the user is currently signed in by checking the absence of 'sign in' text
     * and 'Hello, Sign in' text. When the user is signed in, page content does not contain 'sign in' text and 'Hello, Sign in' text.
     */
    @And("^user is signed in$")
    public void userSignedIn() {
        logger.debug("Step definition - user is signed in");
        try {
            Page currentPage = Page.currentPage();
            assertFalse("Expected - page does not contain 'sign in' text, Actual - page contains 'sign in' text", currentPage.contentContainsIgnoreCase("sign in"));
            assertFalse("Expected - page does not contain 'Hello, sign in' text, Actual - page contains 'Hello, sign in' text", currentPage.contentContainsIgnoreCase("Hello, sign in"));
            logger.debug("STEP OK");
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }

    /**
     * This step definition verifies that the given field is present on the sign in page.
     *
     * @param fieldName name of the field
     */
    @And("^sign in page asks for (.*)$")
    public void signInPageAsksField(String fieldName) {
        try {
            logger.debug("Step definition - sign in page asks for " + fieldName);
            Page currentPage = Page.currentPage();
            Page signInPage = Page.make(ProjectProperties.getHomeUrl(), PageType.SignIn, null);
            // First verify user is on sign in page
            assertEquals("Current page is not sign in page", signInPage.getPathSegment(), currentPage.getPathSegment());
            // Verify that current page contains given field
            assertTrue("Current page does not contain field '" + fieldName + "'", currentPage.contentContains(fieldName));
            logger.debug("STEP OK");
        } catch (MalformedURLException exception) {
            logger.error(exception);
            fail(exception.getMessage());
        } catch (AssertionFailedError assertionFailure) {
            logger.error(assertionFailure);
            throw assertionFailure;
        }
    }
}
