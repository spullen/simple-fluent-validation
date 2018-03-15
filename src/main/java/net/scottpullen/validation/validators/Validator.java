package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

public interface Validator {
    boolean isValid();
    boolean isInvalid();
    ValidationError buildValidationError();
}
