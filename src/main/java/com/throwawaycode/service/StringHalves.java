package com.throwawaycode.service;

/**
 * Provides two halves of a string.  If the string contains an odd number of characters, the split is made to favor the second half.
 * For example, when given "Brian", the first half will be "Br" and the second half "ian".
 */
public class StringHalves {
    private String firstHalf;
    private String secondHalf;

    public static StringHalves splitIntoHalves(CharSequence plaintext) {
        String name = plaintext.toString();
        int nameLength = name.length();
        String firstHalf = name.substring(0, nameLength / 2);
        String secondHalf = name.substring(nameLength / 2);
        return new StringHalves(firstHalf, secondHalf);
    }

    public StringHalves(String firstHalf, String secondHalf) {
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
    }

    public String getFirstHalf() {
        return firstHalf;
    }

    public String getSecondHalf() {
        return secondHalf;
    }

    @Override
    public String toString() {
        return "StringHalves{" +
                "firstHalf='" + firstHalf + '\'' +
                ", secondHalf='" + secondHalf + '\'' +
                '}';
    }
}
