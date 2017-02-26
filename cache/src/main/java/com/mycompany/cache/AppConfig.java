package com.mycompany.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by root on 26.02.17.
 */
@Configuration
public class AppConfig {
    @Bean
    public AbstractCacheUtil cacheUtil() {
        return new CacheUtil(60000L);
    }
}