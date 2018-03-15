package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class GreaterThanOrEqualToValidator implements Validator {
    private static final String KEY_GREATER_THAN_OR_EQUAL_TO = "validation.greaterThanOrEqualTo";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
