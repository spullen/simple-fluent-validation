package net.scottpullen.validation;

import net.scottpullen.validation.validators.NotBlankValidator;
import net.scottpullen.validation.validators.GreaterThanOrEqualToValidator;
import net.scottpullen.validation.validators.GreaterThanValidator;
import net.scottpullen.validation.validators.LessThanOrEqualToValidator;
import net.scottpullen.validation.validators.LessThanValidator;
import net.scottpullen.validation.validators.PresenceAndNotEmptyValidator;
import net.scottpullen.validation.validators.PresenceValidator;
import net.scottpullen.validation.validators.Validator;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.scottpullen.validation.helpers.ArgumentValidation.require;

public class Validation {
    
    private ValidationContext context;
    private boolean eager = false;
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
     * @param eager boolean
     * @param failFast boolean
     */
    private Validation(ValidationContext context, boolean eager, boolean failFast) {
        require(context, "ValidationContext required");
        this.context = context;
        this.eager = eager;
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
        return isValid(new PresenceValidator<T>(object, label));
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
        return isValid(new PresenceValidator<T>(object, label, key));
    }

    /**
     * Determines whether a collection is present or not and has items
     *
     * @param c The collection under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation presenceAndNotEmpty(Collection c, String label) {
        return isValid(new PresenceAndNotEmptyValidator(c, label));
    }

    /**
     * Determines whether a collection is present or not and has items
     *
     * @param c The collection under test
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @return Validation
     */
    public Validation presenceAndNotEmpty(Collection c, String label, String key) {
        return isValid(new PresenceAndNotEmptyValidator(c, label, key));
    }

    /**
     * Determines whether a string is notBlank
     *
     * @param s String under test
     * @param label A string representing what is being tested
     * @return Validation
     */
    public Validation notBlank(String s, String label) {
        return isValid(new NotBlankValidator(s, label));
    }

    /**
     * Determines whether a string is notBlank
     *
     * @param s String under test
     * @param label A string representing what is being tested
     * @param key A string representing a specific message key
     * @return Validation
     */
    public Validation notBlank(String s, String label, String key) {
        return isValid(new NotBlankValidator(s, label, key));
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
        return isValid(new GreaterThanValidator<T>(c, min, label));
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
        return isValid(new GreaterThanValidator<T>(c, min, label, key));
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
        return isValid(new GreaterThanOrEqualToValidator<T>(c, min, label));
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
        return isValid(new GreaterThanOrEqualToValidator<T>(c, min, label, key));
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
        return isValid(new LessThanValidator<T>(c, max, label));
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
        return isValid(new LessThanValidator<T>(c, max, label, key));
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
        return isValid(new LessThanOrEqualToValidator<T>(c, max, label));
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
        return isValid(new LessThanOrEqualToValidator<T>(c, max, label, key));
    }

    /**
     * Accepts any Validator to be tested
     *
     * @param validator Validator
     * @return Validation
     */
    public Validation isValid(Validator validator) {
        context.addValidator(validator);
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
     * Run the validations
     *
     * @return ValidationContext
     */
    public ValidationContext validate() {
        context.validate();
        return context;
    }

    /**
     * Runs the validations and takes an action that accepts a ValidationContext to provide intermediary behavior
     *
     * @param action Consumer that accepts a ValidationContext
     * @return ValidationContext
     */
    public ValidationContext validate(Consumer<ValidationContext> action) {
        context.validate();
        action.accept(context);
        return context;
    }

    /**
     * Checks the validation and throws a ValidationException if there are any
     *
     * @param context ValidationContext
     * @throws ValidationException
     */
    public static void andThrow(ValidationContext context) throws ValidationException {
        if(context.isInvalid()) {
            throw new ValidationException(context);
        }
    }
}
