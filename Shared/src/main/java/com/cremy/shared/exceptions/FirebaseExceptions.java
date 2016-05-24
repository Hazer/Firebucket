package com.cremy.shared.exceptions;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class FirebaseExceptions {

    public static class NullFirebaseInstanceException extends RuntimeException {
        public NullFirebaseInstanceException() {
            super("The firebase instance provided is NULL");
        }
    }

    public static class NullFirebaseValueException extends RuntimeException {
        public NullFirebaseValueException() {
            super("The firebase value provided is NULL");
        }
    }
}
