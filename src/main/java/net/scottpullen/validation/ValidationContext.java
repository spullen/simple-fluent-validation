package net.scottpullen.validation;

import net.scottpullen.validation.validators.Validator;

import java.util.ArrayList;
import java.util.List;

import static net.scottpullen.validation.ArgumentValidation.require;

public class ValidationContext {
    /**
     * A label to describe what it being validated
     */
    private final String label;

    /**
     * A list of Validators to run
     */
    private List<Validator> validators;

    /**
     * A list of ValidationErrors
     */
    private List<ValidationError> errors;

    /**
     * A sub-ValidationContext
     */
    private List<ValidationContext> nestedContexts;


    protected ValidationContext(String label) {
        this.label = label;
        validators = new ArrayList<>();
        errors = new ArrayList<>();
        nestedContexts = new ArrayList<>();
    }

    public String getLabel() { return label; }
    public List<ValidationError> getErrors() {return errors; }
    public List<ValidationContext> getNestedContexts() { return nestedContexts; }

    /**
     * Adds a ValidationError to the list of errors
     * @param error A ValidationError
     */
    protected void addError(ValidationError error) {
        require(error, "ValidationError required");

        errors.add(error);
    }

    /**
     * Adds a ValidationContext relative to a parent context
     *
     * @param context ValidationContext
     */
    protected void addNestedContext(ValidationContext context) {
        this.nestedContexts.add(context);
    }

    /**
     * @param validator Validator to be tested
     */
    protected void addValidator(Validator validator) {
        validators.add(validator);
    }

    /**
     * Run validators for ValidatorContext and any nested ValidatorContexts
     */
    protected void validate() {
        validators.stream()
            .filter(Validator::isInvalid)
            .map(Validator::buildValidationError)
            .forEach(this::addError);

        nestedContexts.forEach(ValidationContext::validate);
    }

    /**
     * Determines if there are any errors
     * @return boolean
     */
    public boolean hasErrors() {
        return !errors.isEmpty() || nestedContexts.stream().anyMatch(ValidationContext::hasErrors);
    }
}
