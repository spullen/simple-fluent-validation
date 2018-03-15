package net.scottpullen.validation

import spock.lang.Specification

import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

class ValidationTest extends Specification {

    void "validation presence for object"() {
        given:
        String name = null

        when: 'the object is null (no presence)'
        new Validation("presence")
            .presence(name, "name")
            .validateAndThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "presence"

        ex1.getContext().hasErrors()

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
            .validateAndThrow();

        then:
        notThrown ValidationException
    }

    void "validation presence for collection"() {
        given:
        List<String> names = null

        when: 'the object is null'
        new Validation("presence")
            .presence(names, "names")
            .validateAndThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "presence"

        ex1.getContext().hasErrors()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.presenceOrEmpty"
        error1.label == "names"
        error1.message == "must be present and not empty"

        when: 'the object is not null, but empty'
        names = []

        new Validation("presence")
            .presence(names, "names")
            .validateAndThrow();

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "presence"

        ex2.getContext().hasErrors()

        List<ValidationError> errors2 = ex2.getContext().getErrors()

        errors2.size() == 1

        ValidationError error2 = errors2.first()

        error2.key == "validation.presenceOrEmpty"
        error2.label == "names"
        error2.message == "names must be present and not empty"

        when: 'the object is not null and not empty'
        names = ['Mr. Tester']

        new Validation("test")
            .presence(names, "names")
            .validateAndThrow();

        then:
        notThrown ValidationException
    }

    void "validation blank"() {
        given:
        String name = ""

        when: 'the object is blank'
        new Validation("blank")
            .blank(name, "name")
            .validateAndThrow();

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "blank"

        ex1.getContext().hasErrors()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.blank"
        error1.label == "name"
        error1.message == "name cannot be blank"

        when:
        name = "Mr. Tester"

        new Validation("blank")
            .blank(name, "name")
            .validateAndThrow();

        then:
        notThrown ValidationException
    }

    void "validation greaterThan for int value"() {
        given:
        Integer count = 9
        Integer min = 10

        when: 'the value is less than the min'
        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThan"

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "greaterThan"

        ex2.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation greaterThan for double value"() {
        given:
        Double count = 9
        Double min = 10

        when: 'the value is less than the min'
        new Validation("greaterThan")
            .greaterThan(count, min, "count")
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThan"

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "greaterThan"

        ex2.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation greaterThanOrEqualTo"() {
        given:
        Integer count = 9
        Integer min = 10

        when: 'the value is less than the min'
        new Validation("greaterThanOrEqualTo")
            .greaterThanOrEqualTo(count, min, "count")
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "greaterThanOrEqualTo"

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        notThrown ValidationException

        when:
        count = 11

        new Validation("greaterThan")
            .greaterThanOrEqualTo(count, min, "count")
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation lessThan"() {
        given:
        Integer count = 11
        Integer max = 10

        when: 'the value is greater than the max'
        new Validation("lessThan")
            .lessThan(count, max, "count")
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "lessThan"

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().label == "lessThan"

        ex2.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation lessThanOrEqualTo"() {
        given:
        Integer count = 11
        Integer max = 10

        when: 'the value is greater than the max'
        new Validation("lessThanOrEqualTo")
            .lessThanOrEqualTo(count, max, "count")
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "lessThanOrEqualTo"

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        notThrown ValidationException

        when:
        count = 9

        new Validation("lessThanOrEqualTo")
            .lessThanOrEqualTo(count, max, "count")
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation isValid, custom validation"() {
        when: 'the custom validator returns a ValidationMessage'
        new Validation("custom")
            .isValid({
                return Optional.of(new ValidationError("customField", "validation.custom", "customField failed custom validation"))
            } as Supplier)
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().label == "custom"

        ex1.getContext().hasErrors()

        List<ValidationError> errors1 = ex1.getContext().getErrors()

        errors1.size() == 1

        ValidationError error1 = errors1.first()

        error1.key == "validation.custom"
        error1.label == "customField"
        error1.message == "customField failed custom validation"

        when: 'the custom validator does not return a ValidationMessage'
        new Validation("custom")
            .isValid({ return Optional.empty() } as Supplier)
            .validateAndThrow()

        then:
        notThrown ValidationException
    }

    void "validation isValid, nested validation"() {
        given:
        String name = ""

        when:
        new Validation("parent")
            .isValid(name, "child", { String o, Validation v ->
                v.blank(o, "name")
            } as BiConsumer)
            .validateAndThrow()

        then:
        ValidationException ex1 = thrown()

        ex1.getContext().hasErrors()

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
            .validateAndThrow()

        then:
        ValidationException ex2 = thrown()

        ex2.getContext().hasErrors()

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

    void "validation fluid"() {
        given:
        String name = "Test"
        Integer count = 10

        Integer min = 8
        Integer max = 12

        when:
        new Validation("fluid")
            .presence(name, "name")
            .presence(count, "count")
            .blank(name, "name")
            .greaterThanOrEqualTo(count, min, "count")
            .lessThanOrEqualTo(count, max, "count")
            .validateAndThrow()

        then:
        notThrown ValidationException

        when:
        String name1 = null
        String otherName = ""

        Integer count1 = 5
        Integer min1 = 5
        Integer max1 = 5

        new Validation("fluid")
            .presence(name1, "name1")
            .blank(otherName, "otherName")
            .greaterThan(count1, min1, "count1")
            .lessThan(count1, max1, "count1")
            .validateAndThrow()

        then:
        ValidationException ex = thrown()

        ex.getContext().label == "fluid"

        ex.getContext().hasErrors()

        List<ValidationError> errors = ex.getContext().getErrors()

        errors.size() == 4

        List<String> labels = errors.collect({ ValidationError error -> error.label }).unique().sort()

        labels == ["count1", "name1", "otherName"]
    }
}
