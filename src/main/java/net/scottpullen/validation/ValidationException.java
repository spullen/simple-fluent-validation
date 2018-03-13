package net.scottpullen.validation;

public class ValidationException extends RuntimeException {

    private final ValidationContext context;

    public ValidationException(final ValidationContext context) {
        this.context = context;

        // group errors into fields for returning to the client
    }

    public ValidationContext getContext() {
        return context;
    }
}
