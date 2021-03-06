package com.throwawaycode;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.statistics.StatisticsGateway;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.throwawaycode.service.NameDecoder;
import com.throwawaycode.service.NameEncoder;
import com.throwawaycode.service.SplitAndMixEncoder;
import com.throwawaycode.service.SplitAndMixReassemblerDecoder;

@SpringBootApplication
@Import(AppConfig.class)
public class NameEncoderDecoderApplication {

    private static final Logger LOG = LoggerFactory.getLogger(NameEncoderDecoderApplication.class);
    private static final String ENCODE = "encode";
    private static final String DECODE = "decode";

    public static void main(String[] args) {
        SpringApplication.run(NameEncoderDecoderApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(NameEncoder nameEncoder, NameDecoder nameDecoder) {

        return args -> {
            String[] input = Arrays.copyOfRange(args, 1, args.length);

            String command = args[0];
            if (command.equals(DECODE)) {
                decodeInput(input, nameDecoder);
                printCacheStatistics(SplitAndMixReassemblerDecoder.DECODER_CACHE_NAME);
            }
            if (command.equals(ENCODE)) {
                encodeInput(input, nameEncoder);
                printCacheStatistics(SplitAndMixEncoder.ENCODED_NAMES_CACHE);
            }

            shutdown();
        };
    }

    private static void encodeInput(String[] input, NameEncoder nameEncoder) {
        StringBuilder output = new StringBuilder();
        for (String plaintext : input) {
            String cipherText = nameEncoder.encode(plaintext);
            output.append(cipherText).append(' ');
        }
        LOG.info("encoding result:{}", output.substring(0, output.length() - 1));
    }

    private static void decodeInput(String[] input, NameDecoder nameDecoder) {
        StringBuilder output = new StringBuilder();
        for (String cipherText : input) {
            output.append(nameDecoder.decode(cipherText)).append(' ');
        }
        LOG.info("decoding result:{}", output.substring(0, output.length() - 1));
    }

    private static void printCacheStatistics(String cacheName) {
        printStatsForCache(cacheName);

    }

    private static void printStatsForCache(String cacheName) {
        CacheManager cacheManager = CacheManager.getInstance();
        Cache decodeCache = cacheManager.getCache(cacheName);
        StatisticsGateway statistics = decodeCache.getStatistics();
        LOG.info("{} cache stats:  hit count:{}, miss count:{}", cacheName, statistics.cacheHitCount(),
                statistics.cacheMissCount());
    }


    private static void shutdown() {
        // process doesn't seem to properly terminate unless shutdown on cachemanager is called explicitly
        CacheManager.getInstance().shutdown();
    }

}
