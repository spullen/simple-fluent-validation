package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

import static net.scottpullen.validation.ArgumentValidation.require;

public class GreaterThanOrEqualToValidator<T> implements Validator {
    private static final String DEFAULT_KEY = "validation.greaterThanOrEqualTo";

    private final Comparable<T> c;
    private final T min;
    private final String label;
    private final String key;

    public GreaterThanOrEqualToValidator(Comparable<T> c, T min, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.c = c;
        this.min = min;
        this.label = label;
        this.key = key;
    }

    public GreaterThanOrEqualToValidator(Comparable<T> c, T min, String label) {
        this(c, min, label, DEFAULT_KEY);
    }

    @Override
    public boolean isValid() {
        return c.compareTo(min) >= 0;
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " must be greater than or equal to " + min);
    }
}
