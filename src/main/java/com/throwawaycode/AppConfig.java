package com.throwawaycode;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.throwawaycode.service.NameDecoder;
import com.throwawaycode.service.NameEncoder;
import com.throwawaycode.service.SplitAndMixEncoder;
import com.throwawaycode.service.SplitAndMixReassemblerDecoder;

@Configuration
@EnableCaching
public class AppConfig {
    @Bean
    NameDecoder nameDecoder() {
        return new SplitAndMixReassemblerDecoder();
    }

    @Bean
    NameEncoder nameEncoder() {
        return new SplitAndMixEncoder();
    }

    @Bean
    CacheManager cacheManager() {
        return new EhCacheCacheManager();
    }
}
