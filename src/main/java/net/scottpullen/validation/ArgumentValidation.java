package net.scottpullen.validation;

public class ArgumentValidation {
    /**
     *
     * @param o An object to check if null
     * @param message A message that will be used in the IllegalArgumentException if thrown
     * @throws IllegalArgumentException
     */
    public static void require(Object o, String message) throws IllegalArgumentException {
        if(o == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
