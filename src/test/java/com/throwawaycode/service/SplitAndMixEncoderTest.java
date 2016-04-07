package com.throwawaycode.service;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.throwawaycode.util.EncoderUtils;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class SplitAndMixEncoderTest {
    private static final int STOP_VERIFYING_AT_LENGTH = 200;
    SplitAndMixEncoder encoder = new SplitAndMixEncoder();

    @Test
    public void should_encode_name_with_even_length_as_expected() {
        String nameWhoseLengthIsEven = "Amin";
        String expectedCipherText = "inAm";

        String encodingResult = encoder.encode(nameWhoseLengthIsEven);
        assertThat(encodingResult, is(expectedCipherText));
    }

    @Test
    public void should_encode_name_with_odd_length_as_expected() {
        String nameWhoseLengthIsOdd = "Vimal";
        String expectedCipherText = "al#Vim";

        String result = encoder.encode(nameWhoseLengthIsOdd);

        assertThat(result, is(expectedCipherText));
    }

    @Test
    public void should_encode_Japanese_name_successfully() {
        String result = encoder.encode("昭夫");

        assertThat(result, is("夫昭"));
    }

    @Test
    public void should_decode_varying_lengths() {
        for (int i = 1; i < STOP_VERIFYING_AT_LENGTH; i++) {
            String plaintext = StringUtils.repeat("a", i);
            String cipherText = encoder.encode(plaintext);
            assertThatNameWasEncoded(plaintext, cipherText);
        }
    }

    private static void assertThatNameWasEncoded(String plaintext, String cipherText) {
        if (EncoderUtils.isOddLength(plaintext)) {
            assertThat(numberOfUniqueCharacters(cipherText), isExactlyTwo());
            assertThat(cipherText, containsString(EncoderUtils.PAD));
        }  else {
            assertThat(cipherText, is(plaintext));
        }
    }

    private static Matcher<Long> isExactlyTwo() {
        return is(2L);
    }

    private static long numberOfUniqueCharacters(String cipherText) {
        return cipherText.chars().distinct().count();
    }

}