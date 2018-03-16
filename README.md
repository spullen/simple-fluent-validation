# Simple Fluent Validation

The goal of this project is to provide an easy to use validation framework.

## Features

* Fluent interface
* Built in validators (see Available Validators below)
* Nestable validations (ex. handling nested items from a form)

## Usage

Creates a new Validation and adds a presence test. The Validators are then run with the `.validate` command. And `.andThrow` tells the Validation to throw a `ValidationException` if there are any errors.
```
String test = null;
new Validation("validation-parent")
    .presence(test, "test")
    .validate()
    .andThrow()
```

Validators can be chained together. 
```
new Validation("MyValidation")
    .presence(test, "test")
    .blank(test, "test")
    ...
```

There are two terminating calls `andThrow` and `collect` (Note `collect` has not implemented yet).

### Available Validators

* presence(T o, String label)
* presenceOrEmpty(Collection c, String label)
* greaterThan(Comparable<T> c, T min, String label)
* greaterThanOrEqualTo(Comparable<T> c, T min, String label)
* lessThan(Comparable<T> c, T max, String label)
* lessThanOrEqualTo(Comparable<T> c, T max, String label)

The helper methods also provide an additional method option of key. Which can be used to correspond to your own message catalog.
Ex. `presence(someObject, "someObjectField", "my.message.key")`

More pre-defined validations to come. If you have one you would like to add create an Issue or submit a PR.
It's also possible to create your own custom Validator (see below).

### Custom Validators

It is possible to create custom validators.

```
class MyValidator implements Validator {

    public boolean isValid() {
        return true;
    }
    
    public boolean isInvalid() {
        return !isValid();
    }
    
    public ValidationError buildValidationError() {
        return new ValidationError("field-label", "some.validation.key", "A message of the failure");
    }

}
```

```
new Validation("custom-validator")
    .isValid(new MyValidator())
    .validate()
    .andThrow();
```

## Recommended Response Format

```
{
  "validation-label": {
    "some-label": [
      {
        "label": "some-label",
        "key": "validation.presence",
        "message": "some-label must be present"
      },
      ...
    ],
    "sub-validation-label": {
      "some-sub-label1": [
        {
          "label": "some-sub-label1",
          "key": "validation.presence",
          "message": "some-sub-label1 must be present"
        }, ...
      ],
      ... more labels, or sub validations
    },
    ... more labels, or sub validations
  }
}
```

## TODO

* Validation result collectors
    * Provide a way to organize a summary for validation results (collectors)
    * Provide different structures for displaying results
    * For example, being able to provide a specific structure that will be passed to the fronted of an application
* Lazy execution
    * Only execute validations on validate
* Fail fast
    * Collect or throw ValidationException after first validation failure
* Conditional validations
* Custom messages

Some scratch ideas for how the api should look based on TODO
```
new Validation("label").
    .lazy() // optional
    .failFast() // optional
    .presence(object, "o")
    ... // more validations
    .validate()
    .throwException()
```
and
```
new Validation("label")
    .lazy() // optional
    .failFast() // optional
    .presence(object, "o")
    ... // more validations
    .validate()
    .collect(ValidationCollector::new) // ValidationCollector for Simple and Complex
```
Want to mimic Java's stream collectors

Conditional validations scratch
```
new Validation("label")
    .presence(object, "o", (validation) -> {
        validation.greaterThan(object, 0, "o")
            .lessThan(object, 10, "o")
    })
    .validate()
    .collect(ValidationCollector::new) // ValidationCollector for Simple and Complex
```
