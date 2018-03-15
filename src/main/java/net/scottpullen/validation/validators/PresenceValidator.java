package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

import static net.scottpullen.validation.ArgumentValidation.require;

public class PresenceValidator<T> implements Validator {
    private static final String DEFAULT_KEY = "validation.presence";

    private final T o;
    private final String label;
    private final String key;

    public PresenceValidator(T o, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.o = o;
        this.label = label;
        this.key = key;
    }

    public PresenceValidator(T o, String label) {
        this(o, label, DEFAULT_KEY);
    }

    @Override
    public boolean isValid() {
        return o != null;
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " must be present");
    }
}
