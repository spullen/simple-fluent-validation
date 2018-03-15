package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

import java.util.Collection;

import static net.scottpullen.validation.ArgumentValidation.require;

public class PresenceOrEmptyValidator implements Validator {
    private static final String KEY_PRESENCE_OR_EMPTY = "validation.presenceOrEmpty";

    private final Collection c;
    private final String label;
    private final String key;

    public PresenceOrEmptyValidator(Collection c, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.c = c;
        this.label = label;
        this.key = key;
    }

    public PresenceOrEmptyValidator(Collection c, String label) {
        this(c, label, KEY_PRESENCE_OR_EMPTY);
    }

    @Override
    public boolean isValid() {
        return c != null && !c.isEmpty();
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " must be present and not empty");
    }
}
