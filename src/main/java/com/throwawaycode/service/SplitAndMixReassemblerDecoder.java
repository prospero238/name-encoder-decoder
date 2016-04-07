package com.throwawaycode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.throwawaycode.util.EncoderUtils;

public class SplitAndMixReassemblerDecoder implements NameDecoder {

    public static final String CACHE_NAME = "encoded_name_cache";

    private static final Logger LOG = LoggerFactory.getLogger(SplitAndMixReassemblerDecoder.class);

    @Override
    @Cacheable(cacheNames = CACHE_NAME)
    public String decode(String ciphertext) {

        StringHalves halves = StringHalves.splitIntoHalves(ciphertext);
        String reassambled = halves.getSecondHalf() + halves.getFirstHalf();

        String result;
        if (reassambled.endsWith(EncoderUtils.PAD)) {
            result = reassambled.substring(0, reassambled.length() - 1);
        } else {
            result = reassambled;
        }

        LOG.debug("given ciphertext '{}', decoded to value '{}'", ciphertext, result);
        return result;
    }
}
