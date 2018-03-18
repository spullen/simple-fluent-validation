package net.scottpullen.validation.helpers;

public class ArgumentValidation {
    /**
     *
     * @param o An object to check if null
     * @param message A message that will be used in the IllegalArgumentException if thrown
     * @throws IllegalArgumentException exception that is thrown if the object supplied is null
     */
    public static void require(Object o, String message) throws IllegalArgumentException {
        if(o == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
