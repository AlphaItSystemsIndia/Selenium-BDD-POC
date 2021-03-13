package com.amazon.test.web.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Represents a text box on the web page.
 * This class finds a text box using its id attribute
 * and provides methods to set/get values in the text.
 */
public class WebTextBox {
    private static final String SELECTOR_TEXT_BOX = "input[type=text]#<id>,textarea#<id>,input[type=search]#<id>,input.a-input-text#<id>";

    // id of the text box
    private final String textBoxId;

    public WebTextBox(String textBoxId) {
        this.textBoxId = textBoxId;
    }

    /**
     * This method finds the {@link WebElement} for the text box using its id in the DOM.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which text box is present
     * @return {@link WebElement} object for the text box
     * @throws NoSuchElementException if text box is not present in the DOM
     */
    private WebElement findTextBoxElement(WebElement rootElement) throws NoSuchElementException {
        String selector = SELECTOR_TEXT_BOX.replaceAll("<id>", this.textBoxId);
        return rootElement.findElement(By.cssSelector(selector));
    }

    /**
     * Sets the text value in the text box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which text box is present
     * @param text        text value to set in text box
     * @throws NoSuchElementException if text box is not present in the DOM
     */
    public void setText(WebElement rootElement, String text) throws NoSuchElementException {
        WebElement textBoxElement = findTextBoxElement(rootElement);
        // clear any existing text
        textBoxElement.clear();
        textBoxElement.sendKeys(text);
    }

    /**
     * Gets the text value from the text box.
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which text box is present
     * @return text value present in the text box
     * @throws NoSuchElementException if text box is not present in the DOM
     */
    public String getText(WebElement rootElement) throws NoSuchElementException {
        WebElement textBoxElement = findTextBoxElement(rootElement);
        return textBoxElement.getAttribute("value");
    }
}
