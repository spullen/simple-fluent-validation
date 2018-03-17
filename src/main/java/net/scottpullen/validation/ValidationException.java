package net.scottpullen.validation;

import static net.scottpullen.validation.helpers.ArgumentValidation.require;

public class ValidationException extends RuntimeException {

    private final ValidationContext context;

    public ValidationException(final ValidationContext context) {
        require(context, "ValidationContext required");

        this.context = context;
    }

    public ValidationContext getContext() {
        return context;
    }
}
