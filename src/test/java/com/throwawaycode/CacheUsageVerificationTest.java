package com.throwawaycode;

import net.sf.ehcache.CacheManager;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.throwawaycode.service.NameDecoder;
import com.throwawaycode.service.NameEncoder;
import com.throwawaycode.service.SplitAndMixEncoder;
import com.throwawaycode.service.SplitAndMixReassemblerDecoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class CacheUsageVerificationTest {
    private static final Logger LOG = LoggerFactory.getLogger(CacheUsageVerificationTest.class);

    @Resource
    protected NameDecoder decoder;

    @Resource
    protected NameEncoder nameEncoder;

    protected String randomCharacters = RandomStringUtils.randomAlphabetic(4);

    protected CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager = CacheManager.getInstance();
    }

    @Test
    public void should_utilize_cache_when_identical_ciphertext_is_decoded() {
        decodeIdenticalCiphertextTwice(randomCharacters);
        assertThat(decoderCacheHitCount(), isExactlyOne());
    }

    @Test
    public void should_use_cache_when_encoding_identical_names() {
        encodeIdenticalPlaintextTwice(randomCharacters);
        assertThat(encoderCacheHitCount(), isExactlyOne());
    }

    private void decodeIdenticalCiphertextTwice(String ciphertext) {
        decoder.decode(ciphertext);
        decoder.decode(ciphertext);
    }

    private void encodeIdenticalPlaintextTwice(CharSequence plaintext) {
        nameEncoder.encode(plaintext.toString());
        nameEncoder.encode(plaintext.toString());
    }

    private long encoderCacheHitCount() {
        return cacheHitCount(SplitAndMixEncoder.ENCODED_NAMES_CACHE);
    }

    private long decoderCacheHitCount() {
        String cacheName = SplitAndMixReassemblerDecoder.DECODER_CACHE_NAME;
        return cacheHitCount(cacheName);
    }

    private long cacheHitCount(String cacheName) {
        return cacheManager.getCache(cacheName).getStatistics().cacheHitCount();
    }

    private static Matcher<Long> isExactlyOne() {
        return is(1L);
    }


}
