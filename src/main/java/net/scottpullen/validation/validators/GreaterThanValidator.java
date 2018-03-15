package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class GreaterThanValidator implements Validator {
    private static final String DEFAULT_KEY = "validation.greaterThan";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
