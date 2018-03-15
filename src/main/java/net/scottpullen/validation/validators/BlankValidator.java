package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import static net.scottpullen.validation.ArgumentValidation.require;

public class BlankValidator implements Validator {
    private static final String DEFAULT_KEY = "validation.blank";

    private final String s;
    private final String label;
    private final String key;

    public BlankValidator(String s, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.s = s;
        this.label = label;
        this.key = key;
    }

    public BlankValidator(String s, String label) {
        this(s, label, DEFAULT_KEY);
    }

    @Override
    public boolean isValid() {
        return !StringUtils.isBlank(s);
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " cannot be blank");
    }
}
