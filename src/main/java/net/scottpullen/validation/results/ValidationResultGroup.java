package net.scottpullen.validation.results;

import static net.scottpullen.validation.ArgumentValidation.require;

public class ValidationResultGroup {
    private final String label;

    public ValidationResultGroup(final String label) {
        require(label, "label required");
        this.label = label;
    }
}
