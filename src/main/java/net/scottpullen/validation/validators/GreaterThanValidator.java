package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class GreaterThanValidator implements Validator {
    private static final String KEY_GREATER_THAN = "validation.greaterThan";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
