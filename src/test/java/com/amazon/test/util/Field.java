package com.amazon.test.util;

import org.openqa.selenium.InvalidArgumentException;

/**
 * Enum to represent form fields.
 * Each field enum contains information about field label, id and type.
 * Field label stores the displayed label for the field.
 * Field id helps in accessing target {@link org.openqa.selenium.WebElement} of the field.
 * Field type helps to determine user interactions that can be performed on field.
 */
public enum Field {
    EmailPhoneField(Field.LABEL_EMAIL_PHONE_FIELD, Field.ID_EMAIL_PHONE_FIELD, FormFieldType.TextField),
    PasswordField(Field.LABEL_PASSWORD_FIELD, Field.ID_PASSWORD_FIELD, FormFieldType.TextField);

    private static final String LABEL_EMAIL_PHONE_FIELD = "Email or mobile phone number";
    private static final String LABEL_PASSWORD_FIELD = "Password";

    private static final String ID_EMAIL_PHONE_FIELD = "ap_email";
    private static final String ID_PASSWORD_FIELD = "ap_password";

    private final String label; // Display label text for the field
    private final String id; // id of the field
    private final FormFieldType type; // type of the field

    /**
     * Creates {@link Field} enum from the display label for the field.
     * Given display label must be known.
     *
     * @param fieldLabel display label for field
     * @return {@link Field} enum for given display label
     * @throws InvalidArgumentException for unknown display label
     */
    public static Field makeFromLabel(String fieldLabel) throws InvalidArgumentException {
        switch (fieldLabel) {
            case LABEL_EMAIL_PHONE_FIELD:
                return EmailPhoneField;
            case LABEL_PASSWORD_FIELD:
                return PasswordField;
            default:
                throw new InvalidArgumentException("Unknown field label - " + fieldLabel);
        }
    }

    /**
     * Constructs a {@link Field} enum.
     *
     * @param label display label for field
     * @param id    id targeting {@link org.openqa.selenium.WebElement} of the field
     * @param type  {@link FormFieldType} enum for field type
     */
    Field(String label, String id, FormFieldType type) {
        this.label = label;
        this.id = id;
        this.type = type;
    }

    /**
     * Get the display label for the field.
     *
     * @return display label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the field id targeting {@link org.openqa.selenium.WebElement} of the field.
     *
     * @return field id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the {@link FormFieldType} enum for the form field type.
     *
     * @return {@link FormFieldType} enum for type of field
     */
    public FormFieldType getType() {
        return type;
    }
}
