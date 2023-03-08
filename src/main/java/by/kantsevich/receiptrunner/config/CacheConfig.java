package by.kantsevich.receiptrunner.config;

import by.kantsevich.receiptrunner.cache.factory.CacheFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheFactory cacheFactory() {
        return new CacheFactory();
    }
}
