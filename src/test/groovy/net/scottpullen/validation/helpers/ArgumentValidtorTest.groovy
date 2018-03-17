package net.scottpullen.validation.helpers

import spock.lang.Specification

class ArgumentValidtorTest extends Specification {
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
