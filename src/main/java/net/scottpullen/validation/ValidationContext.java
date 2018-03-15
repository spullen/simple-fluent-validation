package net.scottpullen.validation;

import java.util.ArrayList;
import java.util.List;

import static net.scottpullen.validation.ArgumentValidation.require;

public class ValidationContext {
    private static final ValidationContext EMPTY_VALIDATION_CONTEXT = new ValidationContext("empty_validation_context");

    private final String label;

    /**
     * A list of ValidationErrors
     */
    private List<ValidationError> errors;

    /**
     * A sub-ValidationContext
     */
    private List<ValidationContext> nestedContexts;


    public ValidationContext(String label) {
        this.label = label;
        errors = new ArrayList<>();
        nestedContexts = new ArrayList<>();
    }

    /**
     * Adds a ValidationError to the list of errors
     * @param error A ValidationError
     */
    public void addError(ValidationError error) {
        require(error, "ValidationError required");

        errors.add(error);
    }

    public String getLabel() { return label; }
    public List<ValidationError> getErrors() { return errors; }

    public void addNestedContext(ValidationContext context) {
        this.nestedContexts.add(context);
    }

    public List<ValidationContext> getNestedContexts() {
        return nestedContexts;
    }

    /**
     * Determines if there are any errors
     * @return boolean
     */
    public boolean hasErrors() {
        return !errors.isEmpty() || nestedContexts.stream().anyMatch(ctx -> ctx.hasErrors());
    }
}
