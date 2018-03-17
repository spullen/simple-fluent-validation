package net.scottpullen.validation

import spock.lang.Specification

import java.util.function.BiConsumer
import java.util.function.Supplier

class ValidationTest extends Specification {

    void "#presence"() {
        given:
        String name = null

        when: 'the object is null (no presence)'
        new Validation("presence")
            .presence(name, "name")
            .validate()
            .andThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "presence"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.presence"
        error1.label == "name"
        error1.message == "name must be present"

        when: 'the object is not null'
        name = "Mr. Tester"

        new Validation("presence")
            .presence(name, "name")
            .validate()
            .andThrow();

        then:
        notThrown ValidationException
    }

    void "#presenceAndNotEmpty"() {
        given:
        List<String> names = null

        when: 'the object is null'
        new Validation("presence")
            .presenceAndNotEmpty(names, "names")
            .validate()
            .andThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "presence"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.presenceOrEmpty"
        error1.label == "names"
        error1.message == "names must be present and not empty"

        when: 'the object is not null, but empty'
        names = []

        new Validation("presence")
            .presenceAndNotEmpty(names, "names")
            .validate()
            .andThrow();

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "presence"

        ex2.getContext().isValid()

        List<ValidationError> errors2 = ex2.getContext().getErrors()

        errors2.size() == 1

        ValidationError error2 = errors2.first()

        error2.key == "validation.presenceOrEmpty"
        error2.label == "names"
        error2.message == "names must be present and not empty"

        when: 'the object is not null and not empty'
        names = ['Mr. Tester']

        new Validation("test")
            .presenceAndNotEmpty(names, "names")
            .validate()
            .andThrow();

        then:
        notThrown ValidationException
    }

    void "#notBlank"() {
        given:
        String name = ""

        when: 'the object is blank'
        new Validation("notBlank")
            .notBlank(name, "name")
            .validate()
            .andThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "notBlank"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.blank"
        error1.label == "name"
        error1.message == "name cannot be blank"

        when:
        name = "Mr. Tester"

        new Validation("notBlank")
            .notBlank(name, "name")
            .validate()
            .andThrow();

        then:
        notThrown ValidationException
    }

    void "#greaterThan for integer"() {
        given:
        Integer count = 9
        Integer min = 10

        when: 'the value is less than the min'
        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThan"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.greaterThan"
        error1.label == "count"
        error1.message == "count must be greater than 10"

        when: 'the value is equal'
        count = 10

        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "greaterThan"

        ex2.getContext().isValid()

        List<ValidationError> errors2 = ex2.getContext().getErrors()

        errors2.size() == 1

        ValidationError error2 = errors2.first()

        error2.key == "validation.greaterThan"
        error2.label == "count"
        error2.message == "count must be greater than 10"

        when:
        count = 11

        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#greaterThan for double"() {
        given:
        Double count = 9
        Double min = 10

        when: 'the value is less than the min'
        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThan"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.greaterThan"
        error1.label == "count"
        error1.message == "count must be greater than 10.0"

        when: 'the value is equal'
        count = 10

        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "greaterThan"

        ex2.getContext().isValid()

        List<ValidationError> errors2 = ex2.getContext().getErrors()

        errors2.size() == 1

        ValidationError error2 = errors2.first()

        error2.key == "validation.greaterThan"
        error2.label == "count"
        error2.message == "count must be greater than 10.0"

        when:
        count = 11

        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#greaterThanOrEqualTo"() {
        given:
        Integer count = 9
        Integer min = 10

        when: 'the value is less than the min'
        new Validation("greaterThanOrEqualTo")
            .greaterThanOrEqualTo(count, min, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThanOrEqualTo"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.greaterThanOrEqualTo"
        error1.label == "count"
        error1.message == "count must be greater than or equal to 10"

        when: 'the value is equal'
        count = 10

        new Validation("greaterThanOrEqualTo")
            .greaterThanOrEqualTo(count, min, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException

        when:
        count = 11

        new Validation("greaterThan")
            .greaterThanOrEqualTo(count, min, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#lessThan"() {
        given:
        Integer count = 11
        Integer max = 10

        when: 'the value is greater than the max'
        new Validation("lessThan")
            .lessThan(count, max, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "lessThan"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.lessThan"
        error1.label == "count"
        error1.message == "count must be less than 10"

        when: 'the value is equal'
        count = 10

        new Validation("lessThan")
            .lessThan(count, max, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "lessThan"

        ex2.getContext().isValid()

        List<ValidationError> errors2 = ex2.getContext().getErrors()

        errors2.size() == 1

        ValidationError error2 = errors2.first()

        error2.key == "validation.lessThan"
        error2.label == "count"
        error2.message == "count must be less than 10"

        when:
        count = 9

        new Validation("lessThan")
            .lessThan(count, max, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#lessThanOrEqualTo"() {
        given:
        Integer count = 11
        Integer max = 10

        when: 'the value is greater than the max'
        new Validation("lessThanOrEqualTo")
            .lessThanOrEqualTo(count, max, "count")
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "lessThanOrEqualTo"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.lessThanOrEqualTo"
        error1.label == "count"
        error1.message == "count must be less than or equal to 10"

        when: 'the value is equal'
        count = 10

        new Validation("lessThanOrEqualTo")
            .lessThanOrEqualTo(count, max, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException

        when:
        count = 9

        new Validation("lessThanOrEqualTo")
            .lessThanOrEqualTo(count, max, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#isValid custom validation"() {
        when: 'the custom validator returns a ValidationMessage'
        new Validation("custom")
            .isValid({
                return Optional.of(new ValidationError("customField", "validation.custom", "customField failed custom validation"))
            } as Supplier)
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "custom"

        ex1.getContext().isValid()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.custom"
        error1.label == "customField"
        error1.message == "customField failed custom validation"

        when: 'the custom validator does not return a ValidationMessage'
        new Validation("custom")
            .isValid({ return Optional.empty() } as Supplier)
            .validate()
            .andThrow()

        then:
        notThrown ValidationException
    }

    void "#isValid nested validation"() {
        given:
        String name = ""

        when:
        new Validation("parent")
            .isValid(name, "child", { String o, Validation v ->
                v.notBlank(o, "name")
            } as BiConsumer)
            .validate()
            .andThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().isValid()

        ex1.getContext().label == "parent"
        ex1.getContext().nestedContexts.size() == 1

        ValidationContext subContext1 = ex1.context.nestedContexts.first()

        subContext1.label == "child"

        when:
        List<Simple> simples = [
                new Simple("Test1"),
                new Simple(null)
        ]

        Complex complex = new Complex(simples)

        new Validation("parent")
            .isValid(complex, "complex", { Complex c, Validation v ->
                c.simples.eachWithIndex { Simple simple, int i ->
                    String label = "simple[" + i + "]"
                    v.presence(simple.name, label + "-name")
                }
            } as BiConsumer)
            .validate()
            .andThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().isValid()

        ex2.getContext().label == "parent"
        ex2.getContext().nestedContexts.size() == 1

        ValidationContext subContext2 = ex2.context.nestedContexts.first()

        subContext2.label == "complex"
        subContext2.errors.size() == 1

        ValidationError error = subContext2.errors.first()

        error.key == "validation.presence"
        error.label == "simple[1]-name"
        error.message == "simple[1]-name must be present"
    }

    void "#merge"() {
        given:
        Validation parentValidation = new Validation("parent")

        Validation childValidation = new Validation("child")
                .notBlank("", "test");

        when:
        parentValidation.validate().andThrow()

        then:
        notThrown ValidationException

        when:
        parentValidation.merge(childValidation)

        parentValidation.validate().andThrow()

        then:
        thrown ValidationException
    }

    void "validation fluent"() {
        given:
        String name = "Test"
        Integer count = 10

        Integer min = 8
        Integer max = 12

        when:
        new Validation("fluent")
            .presence(name, "name")
            .presence(count, "count")
            .notBlank(name, "name")
            .greaterThanOrEqualTo(count, min, "count")
            .lessThanOrEqualTo(count, max, "count")
            .validate()
            .andThrow()

        then:
        notThrown ValidationException

        when:
        String name1 = null
        String otherName = ""

        Integer count1 = 5
        Integer min1 = 5
        Integer max1 = 5

        new Validation("fluent")
            .presence(name1, "name1")
            .notBlank(otherName, "otherName")
            .greaterThan(count1, min1, "count1")
            .lessThan(count1, max1, "count1")
            .validate()
            .andThrow()

        then:
        ValidationException ex = thrown()

        ex.getContext().label == "fluent"

        ex.getContext().isValid()

        List<ValidationError> errors = ex.getContext().getErrors()

        errors.size() == 4

        List<String> labels = errors.collect({ ValidationError error -> error.label }).unique().sort()

        labels == ["count1", "name1", "otherName"]
    }
}
