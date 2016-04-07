package com.throwawaycode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.throwawaycode.util.EncoderUtils;

public class SplitAndMixEncoder implements NameEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(SplitAndMixEncoder.class);
    public static final String ENCODED_NAMES_CACHE = "encoded_names";

    @Override
    @Cacheable(cacheNames = ENCODED_NAMES_CACHE)
    public String encode(String name) {
        StringBuilder plaintext = new StringBuilder(name);
        if (EncoderUtils.isOddLength(plaintext)) {
            appendSingleCharacter(plaintext);
        }

        StringHalves nameHalves = StringHalves.splitIntoHalves(plaintext);

        String result = nameHalves.getSecondHalf() + nameHalves.getFirstHalf();
        LOG.debug("given name '{}', provided encoded value of '{}'", plaintext, result);

        return result;
    }

    private static void appendSingleCharacter(StringBuilder plaintext) {
        plaintext.append(EncoderUtils.PAD_CHARACTER);
    }

}
