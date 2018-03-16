package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

/**
 * Represents a test for a value or values
 * 
 */
public interface Validator {
    /**
     * Tests whether the Validator for the unit under test is valid
     *
     * @return boolean isValid
     */
    boolean isValid();

    /**
     * Tests whether the Validator for the unit under test is not valid
     *
     * This should generally be the inverse of isValid()
     *
     * @return boolean isInvalid
     */
    boolean isInvalid();

    /**
     * Builds a ValidationError for the validator
     *
     * @return ValidationError
     */
    ValidationError buildValidationError();
}
