package com.amazon.test.web.ui;

import com.amazon.test.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents a button presents on the webpage.
 * This class finds a button through button label and
 * it provides methods to perform various button interactions.
 */
public class WebButton {
    private static final String CLASS_BUTTON_CONTAINER = "a-button-inner";
    private static final String SELECTOR_INNER_BUTTON = "input[type=submit]";
    private static final String SELECTOR_ALL_BUTTONS = "button, a[class*=\"button\"]," + "." + CLASS_BUTTON_CONTAINER + ",input[type=submit][value]:not([value=\"\"]),input[type=button][value]:not([value=\"\"])";

    // Button label
    private final String label;

    public WebButton(String label) {
        this.label = label;
    }

    private WebElement findButtonElement() {
        try {
            return findButtonElement(Selenium.browser().findElement(By.tagName("body")));
        } catch (NoSuchElementException exception) {
            return null;
        }
    }

    /**
     * Finds button {@link WebElement} object in the DOM having button label.
     *
     * @param rootElement element in DOM hierarchy where the search for button begins
     * @return {@link WebElement} object for button or null if button is not found
     */
    private WebElement findButtonElement(WebElement rootElement) {
        List<WebElement> allButtonElements = rootElement.findElements(By.cssSelector(SELECTOR_ALL_BUTTONS));
        WebElement targetButton = null;
        for (WebElement buttonElement : allButtonElements) {
            String label;
            String tagName = buttonElement.getTagName();
            if (tagName.equals("input")) {
                label = buttonElement.getAttribute("value");
            } else {
                label = buttonElement.getText();
            }
            if (label != null && getAlphaLabel(label).equals(this.label)) {
                // Found the target button
                targetButton = buttonElement;
                // Element with button container class contains target button inside it
                if (targetButton.getAttribute("class").contains(CLASS_BUTTON_CONTAINER)) {
                    try {
                        targetButton = targetButton.findElement(By.cssSelector(SELECTOR_INNER_BUTTON));
                    } catch (NoSuchElementException exception) {
                        continue;
                    }
                }
                break;
            }
        }
        return targetButton;
    }

    /**
     * Removes special characters and extra spaces from the button label.
     *
     * @param label Original button label
     * @return Label with removed special chars and extra spaces
     */
    private String getAlphaLabel(String label) {
        return label.replaceAll("\\W", " ")
                .replaceAll("\\s{2,}", " ")
                .trim();
    }

    /**
     * Clicks the button.
     *
     * @return true if button is clicked otherwise false.
     */
    public boolean click() {
        return click(null);
    }

    /**
     * Clicks the button
     *
     * @param rootElement root element of DOM hierarchy/sub-hierarchy in which the button exists
     * @return true of button is clicked otherwise false.
     */
    public boolean click(WebElement rootElement) {

        WebElement buttonToPress;
        if (rootElement == null) {
            buttonToPress = findButtonElement();
        } else {
            buttonToPress = findButtonElement(rootElement);
        }

        if (buttonToPress == null) return false;
        buttonToPress.click();
        return true;
    }
}
