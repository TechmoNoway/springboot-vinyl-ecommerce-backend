package com.trikynguci.springbootvinylecommercebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .transactionAware()
                .build();
    }
}
