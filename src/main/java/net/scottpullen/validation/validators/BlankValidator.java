package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public class BlankValidator implements Validator {
    private static final String KEY_BLANK = "validation.blank";

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return null;
    }
}
