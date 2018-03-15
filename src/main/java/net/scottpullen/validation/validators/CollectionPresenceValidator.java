package net.scottpullen.validation.validators;

import net.scottpullen.validation.ValidationError;

import java.util.Collection;

import static net.scottpullen.validation.ArgumentValidation.require;

public class CollectionPresenceValidator implements Validator {
    private static final String KEY_PRESENCE_OR_EMPTY = "validation.presenceOrEmpty";

    private final Collection c;
    private final String label;
    private final String key;

    public CollectionPresenceValidator(Collection c, String label, String key) {
        require(label, "label required");
        require(key, "key required");

        this.c = c;
        this.label = label;
        this.key = key;
    }

    public CollectionPresenceValidator(Collection c, String label) {
        this(c, label, KEY_PRESENCE_OR_EMPTY);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public ValidationError buildValidationError() {
        return new ValidationError(label, key, label + " must be present");
    }
}
