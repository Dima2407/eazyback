package com.kaa_solutions.eazyback.utils;

public final class Validator {

    private static final String NUMBERS = "[0-9]+";

    public static boolean validate(String pField) {
        return pField.matches(NUMBERS);
    }

}
