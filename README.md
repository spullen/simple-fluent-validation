# Simple Fluent Validation

The goal of this project is to provide an easy to use validation framework that is unobtrusive and does not use annotations.
I find that annotation based validations are too framework dependent and can be a pain to customize. Annotations hide implementation details and doesn't make it obvious to what or how it's to be used. Other fluent validation libraries work well, but often I feel like they fall flat in terms of flexibility.

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
    .validate();
```
Or if you want the validation to throw an exception if there are any errors
```
String test = null;
new Validation("validation-parent")
    .presence(test, "test")
    .validate(Validation::andThrow);
```

Validators can be chained together. 
```
new Validation("MyValidation")
    .presence(test, "test")
    .notBlank(test, "test")
    ...
```

There are two terminating calls `andThrow` and `collect` (Note `collect` has not implemented yet).

You can call upon the validation in any layer that you would like. For instance in your service layer. Or if you wanted to only create valid objects in a constructor. Or, provide a method on your object that runs the validation.

### Available Validators

* presence(T o, String label)
* presenceAndNotEmpty(Collection c, String label)
* notBlank(String s, String label)
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
    .validate(Validation::andThrow);
```

## Recommended JSON Response Format

Adding serializers for jackson is in the pipeline.

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

* Jackson serializers for ValidationContext
* Eager execution
    * Executes validators immediately
    * Defaults to false
* Fail fast
    * Collect or throw ValidationException after first validation failure
    * Defaults to false
* Conditional validations
* Custom messages

Some scratch ideas for how the api should look based on TODO
```
new Validation("label").
    .eager() // optional
    .failFast() // optional
    .presence(object, "o")
    ... // more validations
    .validate(Validation::andThrow);
```

Conditional validations scratch
```
new Validation("label")
    .presence(object, "o", (validation) -> {
        validation.greaterThan(object, 0, "o")
            .lessThan(object, 10, "o")
    })
    .validate(Validation::andThrow);
```
