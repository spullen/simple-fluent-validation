package net.scottpullen.validation;

import static net.scottpullen.validation.ArgumentValidation.require;

public final class ValidationError {
    private final String label;
    private final String key;
    private final String message;

    /**
     * @param label A string representing what the corresponding error is related to (i.e. a field from a form, etc...)
     * @param key A string representing a message key (ex. validation.presence)
     * @param message A string representing a full message for the validation error ("label is required")
     */
    public ValidationError(String label, String key, String message) {
        require(label, "label required");
        require(key, "key required");

        this.label = label;
        this.key = key;
        this.message = message;
    }

    public String getLabel() { return label; }
    public String getKey() { return key; }
    public String getMessage() { return message; }
}
