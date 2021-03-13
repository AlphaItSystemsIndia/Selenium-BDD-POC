package com.amazon.test.web.ui;

import com.amazon.test.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

/**
 * Represents a form on the webpage.
 * It is used to fill the values in the form fields.
 */
public class WebForm {

    /**
     * Finds the form element on the page.
     *
     * @return {@link WebElement} object for form
     * @throws NoSuchElementException if form element is not found in DOM
     */
    private WebElement findFormElement() throws NoSuchElementException {
        return Selenium.browser().findElement(By.tagName("form"));
    }

    /**
     * Set the value in the text field of form.
     *
     * @param textFieldId unique id of the text field for accessing text field element in DOM
     * @param value       value to set in the text field
     * @throws NoSuchElementException if form element or text field is not found in DOM
     */
    public void setText(String textFieldId, String value) throws NoSuchElementException {
        if (value == null) value = "";
        WebElement formElement = findFormElement();
        WebTextBox textBox = new WebTextBox(textFieldId);
        textBox.setText(formElement, value);
    }

    /**
     * Select a value in the select box field of form.
     *
     * @param selectFieldId unique id of the select box field for accessing select box element in DOM
     * @param value         option to select in the select box
     * @throws NoSuchElementException     if form element or given option is not found in DOM
     * @throws UnexpectedTagNameException if select box element does not have select tag
     */
    public void selectOption(String selectFieldId, String value) throws NoSuchElementException, UnexpectedTagNameException {
        if (value == null) value = "";
        WebElement formElement = findFormElement();
        WebSelectBox selectBox = new WebSelectBox(selectFieldId);
        if (value.isEmpty()) selectBox.selectFirstOption(formElement);
        else selectBox.selectOption(formElement, value);
    }
}
