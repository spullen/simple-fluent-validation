package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class LessThanOrEqualToValidator implements Validator {
    private static final String DEFAULT_KEY = "validation.lessThanOrEqualTo";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
