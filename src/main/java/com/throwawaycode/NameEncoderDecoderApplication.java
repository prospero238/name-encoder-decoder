package com.throwawaycode;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.statistics.StatisticsGateway;

import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.throwawaycode.service.NameDecoder;
import com.throwawaycode.service.NameEncoder;
import com.throwawaycode.service.SplitAndMixReassemblerDecoder;

@SpringBootApplication

@Import(AppConfig.class)
public class NameEncoderDecoderApplication {

	public static void main(String[] args) {
		System.setProperty(CacheManager.ENABLE_SHUTDOWN_HOOK_PROPERTY, "true");
		SpringApplication.run(NameEncoderDecoderApplication.class, args);
	}


	private static final Logger LOG = LoggerFactory.getLogger(NameEncoderDecoderApplication.class);

	@Bean
	CommandLineRunner commandLineRunner(NameEncoder nameEncoder, NameDecoder nameDecoder) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				String[] input = Arrays.copyOfRange(args, 1, args.length);
				StringBuilder output = new StringBuilder();
				if (Objects.equals(args[0], "decode")) {
					for (int i = 0; i < input.length; i++) {
						String cipherText = input[i];
						output.append(nameDecoder.decode(cipherText)).append(" ");
					}

					LOG.info("decoding result:{}", output.substring(0, output.length()-1));

				}
				if (args[0].equals("encode")) {
					for (int i = 0; i < input.length; i++) {
						String plaintext = input[i];
						String cipherText = nameEncoder.encode(plaintext);
						output.append(cipherText).append(" ");
					}
					LOG.info("encoding result:{}", output.substring(0, output.length() - 1));
				}

				CacheManager instance = CacheManager.getInstance();
				Cache cache = instance.getCache(SplitAndMixReassemblerDecoder.CACHE_NAME);
				StatisticsGateway statistics = cache.getStatistics();
				LOG.info("cache stats:  hit count:{}, miss count:{}", statistics.cacheHitCount(), statistics.cacheMissCount());
				instance.shutdown();

			}
		};
	}

}
