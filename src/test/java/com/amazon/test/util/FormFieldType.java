package com.amazon.test.util;

/**
 * Enum to represent types of form fields.
 * Form field can be of different types like text field, select field, radio field,etc.
 * Field type determines the types of user interactions that can be performed on the field.
 * For example -
 * <p>
 * In case of text field, user can set/get the text.
 * <p>
 * In case of select field, user can choose an option.
 */
public enum FormFieldType {
    TextField, SelectField
}
