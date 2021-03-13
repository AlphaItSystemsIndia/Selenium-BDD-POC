package com.amazon.test.web.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

/**
 * Represents a select box on the webpage.
 * This class finds a select box using its id attribute
 * and provides method to perform various interactions on select box.
 */
public class WebSelectBox {
    private static final String SELECTOR_SELECT_BOX = "select#<id>";

    // id of the select box
    private final String selectBoxId;

    public WebSelectBox(String selectBoxId) {
        this.selectBoxId = selectBoxId;
    }

    /**
     * This methods finds the {@link Select} object for select box using its id in the DOM.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which select box is present
     * @return {@link Select} object for select box
     * @throws NoSuchElementException     if select box for the id is not found in the DOM
     * @throws UnexpectedTagNameException if select box element in DOM does not have select tag
     */
    private Select findSelectBox(WebElement rootElement) throws NoSuchElementException, UnexpectedTagNameException {
        String selector = SELECTOR_SELECT_BOX.replaceAll("<id>", selectBoxId);
        WebElement selectBoxElement = rootElement.findElement(By.cssSelector(selector));
        return new Select(selectBoxElement);
    }

    /**
     * This method selects an option in the select box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which select box is present
     * @param optionText  option to select in the select box
     * @throws NoSuchElementException     if select box or option is not present in the DOM
     * @throws UnexpectedTagNameException if select box element in DOM does not have select tag
     */
    public void selectOption(WebElement rootElement, String optionText) throws NoSuchElementException, UnexpectedTagNameException {
        Select selectBox = findSelectBox(rootElement);
        selectBox.selectByVisibleText(optionText);
    }

    /**
     * This method selects first option in the select box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which select box is present
     * @throws NoSuchElementException     if select box is not present in the DOM
     * @throws UnexpectedTagNameException if select box in DOM element does not have select tag
     */
    public void selectFirstOption(WebElement rootElement) throws NoSuchElementException, UnexpectedTagNameException {
        Select selectBox = findSelectBox(rootElement);
        selectBox.selectByIndex(0);
    }

    /**
     * This method is used to get the selected option in the select box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which select box is present
     * @return selected option text
     * @throws NoSuchElementException if select box is not present in the DOM
     */
    public String getSelectedOption(WebElement rootElement) throws NoSuchElementException {
        Select selectBox = findSelectBox(rootElement);
        return selectBox.getFirstSelectedOption().getText();
    }

    /**
     * This method is used to get the first option in the select box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which select box is present
     * @return first option text
     * @throws NoSuchElementException if select box is not present in the DOM
     */
    public String getFirstOption(WebElement rootElement) throws NoSuchElementException {
        Select selectBox = findSelectBox(rootElement);
        return selectBox.getOptions().get(0).getText();
    }
}
