package com.throwawaycode.util;

public class EncoderUtils {

    public static final char PAD_CHARACTER = '#';
    public static final String PAD = PAD_CHARACTER + "";


    private EncoderUtils() {
    }

    public static boolean isOddLength(CharSequence plaintext) {
        return plaintext.length() % 2 > 0;
    }
}
