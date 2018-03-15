package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class LessThanOrEqualToValidator implements Validator {
    private static final String KEY_LESS_THAN_OR_EQUAL_TO = "validation.lessThanOrEqualTo";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
