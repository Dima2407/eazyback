package com.easyback.andriy.eazyback.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {

private static final String NUMBERS = "[0-9]+";

    public static boolean validate(String pField){
        return pField.matches(NUMBERS);
    }

}
