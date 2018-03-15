# Simple Fluent Validation

The goal of this project is to provide an easy to use validation framework.

## Features

* Fluent interface
* Common validation tests
* Nestable validations (ex. handling nested items from a form)

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

* Configurable messages keys
* Custom messages
* Lazy execution
    * Only execute validations on validate
* Fail fast
    * Collect or throw ValidationException after first validation failure
* Provide a way to organize a summary for validation results
    * Provide different structures for displaying results
    * For example, being able to provide a specific structure that will be passed to the fronted of an application
* Abstract "Validators" into their own class to make it easier to create new ones
* Improved fluent API
* Conditional validations

Ideally would like
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

Possible changes:
* Throw away messages and just use message keys?
