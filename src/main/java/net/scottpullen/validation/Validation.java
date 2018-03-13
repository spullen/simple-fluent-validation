package net.scottpullen.validation;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static net.scottpullen.validation.ArgumentValidation.require;

public class Validation {

    private static final String KEY_PRESENCE = "validation.presence";
    private static final String KEY_BLANK = "validation.blank";
    private static final String KEY_GREATER_THAN = "validation.greaterThan";
    private static final String KEY_GREATER_THAN_OR_EQUAL_TO = "validation.greaterThanOrEqualTo";
    private static final String KEY_LESS_THAN = "validation.lessThan";
    private static final String KEY_LESS_THAN_OR_EQUAL_TO = "validation.lessThanOrEqualTo";

    private ValidationContext context;
    private boolean lazy = false;
    private boolean failFast = false;

    /**
     *
     * @param label A string representing the label of the Validation being done
     */
    public Validation(String label) {
        require(label, "label required");
        context = new ValidationContext(label);
    }

    /**
     *
     * @param context ValidationContext
     * @param lazy boolean
     * @param failFast boolean
     */
    private Validation(ValidationContext context, boolean lazy, boolean failFast) {
        require(context, "ValidationContext required");
        this.context = context;
        this.lazy = lazy;
        this.failFast = failFast;
    }

    /**
     * Determines whether an object is present or not
     *
     * @param object Object under test
     * @param label A string representing what is being tested
     * @param <T> The type of the object under test
     * @return Validation
     */
    public <T> Validation presence(T object, String label) {
        if(object == null) {
            context.addError(new ValidationError(label, KEY_PRESENCE, label + " must be present"));
        }
        return this;
    }

    /**
     * Determines whether a collection is present or not and has items
     *
     * @param c The collection under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation presence(Collection c, String label) {
        if(c == null || c.isEmpty()) {
            context.addError(new ValidationError(label, KEY_PRESENCE, label + " must be present"));
        }
        return this;
    }

    /**
     * Determines whether a string is blank
     *
     * @param s String under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation blank(String s, String label) {
        if(StringUtils.isAllBlank(s)) {
            context.addError(new ValidationError(label, KEY_BLANK, label + " cannot be blank"));
        }
        return this;
    }

    /**
     * Determines whether a value is greater than a minimum value
     *
     * @param c The comparable object under test
     * @param min The minimum value the object under test has to be
     * @param label A string representing what is being tested
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation greaterThan(Comparable<T> c, T min, String label) {
        if(c.compareTo(min) <= 0) {
            context.addError(new ValidationError(label, KEY_GREATER_THAN, label + " must be greater than " + min));
        }
        return this;
    }

    /**
     * Determines whether a value is greater than or equal to a minimum value
     *
     * @param c The comparable object under test
     * @param min The minimum value the object under test has to be
     * @param label A string representing what is being tested
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation greaterThanOrEqualTo(Comparable<T> c, T min, String label) {
        if(c.compareTo(min) < 0) {
            context.addError(new ValidationError(label, KEY_GREATER_THAN_OR_EQUAL_TO, label + " must be greater than or equal to " + min));
        }
        return this;
    }

    /**
     * Determines whether a value is less than a minimum value
     *
     * @param c The comparable object under test
     * @param max The maximum value the object under test has to be
     * @param label A string representing what is being tested
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation lessThan(Comparable<T> c, T max, String label) {
        if(c.compareTo(max) >= 0) {
            context.addError(new ValidationError(label, KEY_LESS_THAN, label + " must be less than " + max));
        }
        return this;
    }

    /**
     * Determines whether a value is less than a minimum value
     *
     * @param c The comparable object under test
     * @param max The maximum value the object under test has to be
     * @param label A string representing what is being tested
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation lessThanOrEqualTo(Comparable<T> c, T max, String label) {
        if(c.compareTo(max) > 0) {
            context.addError(new ValidationError(label, KEY_LESS_THAN_OR_EQUAL_TO, label + " must be less than or equal to " + max));
        }
        return this;
    }

    /**
     * Validates a nested resource
     *
     * @param o Object under test
     * @param label A String to label the object
     * @param nested A function that is passed the object o and a Validation
     * @param <T> The type of o
     * @return Validation
     */
    public <T> Validation isValid(T o, String label, BiConsumer<T, Validation> nested) {
        ValidationContext nestedContext = new ValidationContext(label);
        context.addNestedContext(nestedContext);
        nested.accept(o, new Validation(nestedContext, lazy, failFast));
        return this;
    }

    /**
     * @param validator A function that returns an Optional ValidationError
     * @return Validation
     */
    public Validation isValid(Supplier<Optional<ValidationError>> validator) {
        Optional<ValidationError> maybeMessage = validator.get();
        maybeMessage.ifPresent(context::addError);
        return this;
    }

    /**
     * Checks the validation and throws a ValidationException if there are any
     *
     * @throws ValidationException
     */
    public void validateAndThrow() throws ValidationException {
        if(context.hasErrors()) {
            throw new ValidationException(context);
        }
    }

    /**
     * TODO
     *
     * Check if the validation has any errors and collect all ValidationError
     */
    public void validateAndCollect() {
        throw new NotImplementedException("validateAndCollect has not been implemented yet");
    }
}
