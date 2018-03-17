package net.scottpullen.validation.helpers

import spock.lang.Specification

class ArgumentValidatorTest extends Specification {
    // really just to get 100% test coverage...
    void "constructor"() {
        when:
        ArgumentValidation argumentValidation = new ArgumentValidation()

        then:
        argumentValidation != null
    }

    void "#require"() {
        given:
        String test = null

        when:
        ArgumentValidation.require(test, "test required");

        then:
        thrown IllegalArgumentException

        when:
        test = "test"

        ArgumentValidation.require(test, "test required");

        then:
        notThrown IllegalArgumentException
    }
}
