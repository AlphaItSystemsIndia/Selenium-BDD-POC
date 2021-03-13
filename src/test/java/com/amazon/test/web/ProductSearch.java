package com.amazon.test.web;

import com.amazon.test.Selenium;
import com.amazon.test.web.page.Page;
import com.amazon.test.web.ui.WebButton;
import com.amazon.test.web.ui.WebSelectBox;
import com.amazon.test.web.ui.WebTextBox;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import java.util.regex.Pattern;

/**
 * Class providing operations for searching a product on the website.
 */
public final class ProductSearch {
    private static final String ID_SEARCH_FORM = "nav-search-bar-form"; // search form id
    private static final String ID_SEARCH_FIELD = "twotabsearchtextbox"; // id of the search text field
    private static final String LABEL_SUBMIT_BUTTON = "Go"; // Label for the search button
    private static final String ID_CATEGORY_SELECT = "searchDropdownBox"; // id of the category select box
    // Pattern to capture search results summary line.
    private static final Pattern PATTERN_RESULTS_COUNT = Pattern.compile("\\d+-\\d+ of (over )?(\\d+[,\\d+]*) results for \"\\w+\"", Pattern.CASE_INSENSITIVE);

    private ProductSearch() {
    }

    /**
     * Finds the search form element in the DOM of current webpage.
     *
     * @return search form element
     * @throws NoSuchElementException if search form element not found in DOM
     */
    private static WebElement findFormElement() throws NoSuchElementException {
        return Selenium.browser().findElement(By.id(ID_SEARCH_FORM));
    }

    /**
     * Sets the search keyword in the search text field.
     *
     * @param searchKeyword search keyword
     * @throws NoSuchElementException if search form or search text field not found in DOM
     */
    public static void setKeyword(String searchKeyword) throws NoSuchElementException {
        WebElement searchFormElement = findFormElement();
        WebTextBox searchTextBox = new WebTextBox(ID_SEARCH_FIELD);
        searchTextBox.setText(searchFormElement, searchKeyword);
    }

    /**
     * Selects an option in the categories select box.
     *
     * @param categoryName category name option to select
     * @throws NoSuchElementException     if search form or category option not found
     * @throws UnexpectedTagNameException if category select box is not a select element
     */
    public static void selectCategory(String categoryName) throws NoSuchElementException, UnexpectedTagNameException {
        WebElement searchFormElement = findFormElement();
        WebSelectBox categorySelectBox = new WebSelectBox(ID_CATEGORY_SELECT);
        categorySelectBox.selectOption(searchFormElement, categoryName);
    }

    /**
     * Submits the search form and search results page is loaded.
     *
     * @return true if form submit button is clicked otherwise false.
     * @throws NoSuchElementException if search form is not found in the DOM
     */
    public static boolean submit() throws NoSuchElementException {
        WebElement searchFormElement = findFormElement();
        WebButton submitButton = new WebButton(LABEL_SUBMIT_BUTTON);
        return submitButton.click(searchFormElement);
    }

    /**
     * Checks whether page has search results or not. This method is intended to be called after invoking
     * {@link ProductSearch#submit()}.
     *
     * @return true if page displays search results otherwise false
     */
    public static boolean hasResults() {
        Page currentPage = Page.currentPage();
        String resultsCount = currentPage.findContent(PATTERN_RESULTS_COUNT, 2);
        return resultsCount != null;
    }
}
