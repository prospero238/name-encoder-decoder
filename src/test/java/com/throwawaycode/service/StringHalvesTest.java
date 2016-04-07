package com.throwawaycode.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringHalvesTest {

    @Test
    public void should_split_even_number_characters_evenly() {
        int stringLength = 4;
        StringHalves stringHalves = halveStringOfLength(stringLength);

        assertThat(stringHalves.getFirstHalf().length(), is(stringLength / 2));
        assertThat(stringHalves.getSecondHalf(), is(stringLength / 2));
    }

    @Test
    public void should_split_favoring_second_half_with_odd_number_of_chars() {
        StringHalves stringHalves = halveStringOfLength(5);
        assertThat(isSecondHalfLargerThanFirstHalf(stringHalves), is(true));
    }

    private static StringHalves halveStringOfLength(int stringLength) {
        return StringHalves.splitIntoHalves(RandomStringUtils.randomAlphabetic(stringLength));
    }

    private static boolean isSecondHalfLargerThanFirstHalf(StringHalves stringHalves) {
        return stringHalves.getSecondHalf().length() > stringHalves.getFirstHalf().length();
    }


}