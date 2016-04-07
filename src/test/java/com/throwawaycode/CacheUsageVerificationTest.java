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
import com.throwawaycode.service.SplitAndMixReassemblerDecoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class CacheUsageVerificationTest {
    private static final Logger LOG = LoggerFactory.getLogger(CacheUsageVerificationTest.class);

    @Resource
    protected NameDecoder decoder;
    protected String ciphertext = RandomStringUtils.randomAlphabetic(4);
    protected CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager = CacheManager.getInstance();
    }

    @Test
    public void should_utilize_cache_when_identical_ciphertext_is_decoded() {
        decodeIdenticalCiphertextTwice(ciphertext);
        assertThat(nameCacheHitCount(), isExactlyOne());
    }

    private long nameCacheHitCount() {
        return cacheManager.getCache(SplitAndMixReassemblerDecoder.CACHE_NAME).getStatistics().cacheHitCount();
    }

    private void decodeIdenticalCiphertextTwice(String ciphertext) {
        decoder.decode(ciphertext);
        decoder.decode(ciphertext);
    }

    private static Matcher<Long> isExactlyOne() {
        return is(1L);
    }


}
