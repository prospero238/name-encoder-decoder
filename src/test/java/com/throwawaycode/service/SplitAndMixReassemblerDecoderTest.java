package com.throwawaycode.service;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SplitAndMixReassemblerDecoderTest {

    SplitAndMixReassemblerDecoder reassembler = new SplitAndMixReassemblerDecoder();

    @Test
    public void should_decode_name_with_even_length_as_expected() {
        String cipherText = "inAm";
        String expectedPlaintext = "Amin";

        String result = reassembler.decode(cipherText);

        assertThat(result, is(expectedPlaintext));
    }

    @Test
    public void should_decode_name_with_odd_length_as_expected() {
        String cipherText = "al#Vim";
        String expectedResult = "Vimal";

        String result = reassembler.decode(cipherText);

        assertThat(result, is(expectedResult));
    }

    @Test
    public void should_preserve_name_ending_with_pound_symbol() {
        String cipherText = "a##Vim";
        String expectedResult = "Vima#";
        assertThat(reassembler.decode(cipherText), is(expectedResult));

    }

}