package net.scottpullen.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.scottpullen.validation.ArgumentValidation.require;

/**
 * Container to capture ValidationErrors for a given label
 *
 * A label represents something that can accrue errors against it. A good example of something that would be
 * considered a label would be a field from a form that's being validated.
 *
 * A ValidationErrorGroup can also have nested ValidationItems that contain additional errors. A good example of
 * this would be a nested form (ex. a person's contact information where you can have n number of phone
 * numbers, where each phone number should be validated).
 *
 */
public class ValidationErrorGroup {
    private final String label;

    /**
     * List of errors associated with the label of this ValidationErrorGroup
     */
    private List<ValidationError> errors = new ArrayList<>();

    /**
     * Nested ValidationItems
     */
    private Map<String, ValidationErrorGroup> items = new HashMap<>();

    public ValidationErrorGroup(String label) {
        require(label, "label required");

        this.label = label;
    }

    public String getLabel() { return label; }
    public List<ValidationError> getErrors() { return errors; }
    public Map<String, ValidationErrorGroup> getItems() { return items; }

    public void addError(ValidationError error) {
        require(error, "ValidationError required");

        errors.add(error);
    }

    public void addValidationItem(String label, ValidationErrorGroup item) {
        require(label, "label required");
        require(item, "ValidationErrorGroup required");

        items.put(label, item);
    }
}
