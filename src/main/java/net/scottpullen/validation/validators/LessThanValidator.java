package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class LessThanValidator implements Validator {
    private static final String DEFAULT_KEY = "validation.lessThan";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
