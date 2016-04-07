package com.throwawaycode.service;

public class StringHalves {
    String firstHalf;
    String secondHalf;

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
