package com.throwawaycode.util;

public class EncoderUtils {

    public static final String PAD = "#";


    private EncoderUtils() {
    }

    public static boolean isOddLength(CharSequence plaintext) {
        return plaintext.length() % 2 > 0;
    }
}
