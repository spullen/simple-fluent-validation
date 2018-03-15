package net.scottpullen.validation;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static net.scottpullen.validation.ArgumentValidation.require;

public class Validation {

    private static final String KEY_PRESENCE = "validation.presence";
    private static final String KEY_PRESENCE_OR_EMPTY = "validation.presenceOrEmpty";
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

    private ValidationContext getContext() {
        return this.context;
    }

    /**
     * Merge a context from another Validation into the current Validation
     *
     * @param otherValidation Validation
     * @return Validation
     */
    public Validation merge(Validation otherValidation) {
        require(otherValidation, "otherValidation required");

        context.addNestedContext(otherValidation.getContext());
        return this;
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
        return presence(object, label, KEY_PRESENCE);
    }

    /**
     * Determines whether an object is present or not
     *
     * @param object Object under test
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @param <T> The type of the object under test
     * @return Validation
     */
    public <T> Validation presence(T object, String label, String key) {
        if(object == null) {
            context.addError(new ValidationError(label, key, label + " must be present"));
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
        return presence(c, label, KEY_PRESENCE_OR_EMPTY);
    }

    /**
     * Determines whether a List is present or not and has items
     *
     * @param l The List under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation presence(List l, String label) {
        return presence((Collection) l, label, KEY_PRESENCE_OR_EMPTY);
    }

    /**
     * Determines whether a Set is present or not and has items
     *
     * @param s The Set under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation presence(Set s, String label) {
        return presence((Collection) s, label, KEY_PRESENCE_OR_EMPTY);
    }

    /**
     * Determines whether a collection is present or not and has items
     *
     * @param c The collection under test
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @return Validation
     */
    public Validation presence(Collection c, String label, String key) {
        if(c == null || c.isEmpty()) {
            context.addError(new ValidationError(label, key, label + " must be present and not empty"));
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
        return blank(s, label, KEY_BLANK);
    }

    /**
     * Determines whether a string is blank
     *
     * @param s String under test
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @return Validation
     */
    public Validation blank(String s, String label, String key) {
        if(StringUtils.isAllBlank(s)) {
            context.addError(new ValidationError(label, key, label + " cannot be blank"));
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
        return greaterThan(c, min, label, KEY_GREATER_THAN);
    }

    /**
     * Determines whether a value is greater than a minimum value
     *
     * @param c The comparable object under test
     * @param min The minimum value the object under test has to be
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation greaterThan(Comparable<T> c, T min, String label, String key) {
        if(c.compareTo(min) <= 0) {
            context.addError(new ValidationError(label, key, label + " must be greater than " + min));
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
        return greaterThanOrEqualTo(c, min, label, KEY_GREATER_THAN_OR_EQUAL_TO);
    }

    /**
     * Determines whether a value is greater than or equal to a minimum value
     *
     * @param c The comparable object under test
     * @param min The minimum value the object under test has to be
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation greaterThanOrEqualTo(Comparable<T> c, T min, String label, String key) {
        if(c.compareTo(min) < 0) {
            context.addError(new ValidationError(label, key, label + " must be greater than or equal to " + min));
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
        return lessThan(c, max, label, KEY_LESS_THAN);
    }

    /**
     * Determines whether a value is less than a minimum value
     *
     * @param c The comparable object under test
     * @param max The maximum value the object under test has to be
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation lessThan(Comparable<T> c, T max, String label, String key) {
        if(c.compareTo(max) >= 0) {
            context.addError(new ValidationError(label, key, label + " must be less than " + max));
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
        return lessThanOrEqualTo(c, max, label, KEY_LESS_THAN_OR_EQUAL_TO);
    }

    /**
     * Determines whether a value is less than a minimum value
     *
     * @param c The comparable object under test
     * @param max The maximum value the object under test has to be
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @param <T> The type of the comparable object
     * @return Validation
     */
    public <T> Validation lessThanOrEqualTo(Comparable<T> c, T max, String label, String key) {
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
