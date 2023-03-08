package by.kantsevich.receiptrunner.cache.factory;

import by.kantsevich.receiptrunner.cache.Cache;
import by.kantsevich.receiptrunner.cache.impl.LFUCache;
import by.kantsevich.receiptrunner.cache.impl.LRUCache;
import by.kantsevich.receiptrunner.exception.CacheNotDefinedException;

import org.springframework.beans.factory.annotation.Value;

public class CacheFactory {

    @Value("${cache.algorithm:LRU}")
    private String algorithm;
    @Value("${cache.capacity:10}")
    private int capacity;

    public <K, V> Cache<K, V> createCache() {
        return switch (algorithm) {
            case "LRU" -> new LRUCache<>(capacity);
            case "LFU" -> new LFUCache<>(capacity);
            default -> throw new CacheNotDefinedException("Cache algorithm not defined");
        };
    }
}
