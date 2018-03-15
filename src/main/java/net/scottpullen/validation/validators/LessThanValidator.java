package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

import static net.scottpullen.validation.ArgumentValidation.require;

public class LessThanValidator<T> implements Validator {
    private static final String DEFAULT_KEY = "validation.lessThan";

    private final Comparable<T> c;
    private final T max;
    private final String label;
    private final String key;

    public LessThanValidator(Comparable<T> c, T max, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.c = c;
        this.max = max;
        this.label = label;
        this.key = key;
    }

    public LessThanValidator(Comparable<T> c, T max, String label) {
        this(c, max, label, DEFAULT_KEY);
    }

    @Override
    public boolean isValid() {
        return c.compareTo(max) < 0;
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " must be less than " + max);
    }
}
