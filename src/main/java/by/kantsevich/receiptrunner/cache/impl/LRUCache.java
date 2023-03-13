package by.kantsevich.receiptrunner.cache.impl;

import by.kantsevich.receiptrunner.cache.Cache;

import lombok.Data;

import java.util.*;

/**
 * An implementation of the {@link Cache} interface that uses the least recently used (LRU) algorithm,
 * where the key-value mappings that has not been used the longest is evicted from the cache.
 * {@link LinkedHashMap} is used to store key-value mappings, including their order.
 * The least recently used item is stored at the beginning of the cache.
 *
 * @param <K> {@inheritDoc}
 * @param <V> {@inheritDoc}
 * @author Ruslan Kantsevich
 */
@Data
public class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;

    /**
     * Creates an LRUCache instance with the specified capacity
     *
     * @param capacity the maximum number of entries that this cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity);
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<V> get(K key) {
        if (!cache.containsKey(key)) {
            return Optional.empty();
        }

        V removeValue = cache.remove(key);
        cache.put(key, removeValue);
        V value = cache.get(key);

        return Optional.ofNullable(value);
    }

    /**
     * {@inheritDoc}
     *
     * @param key   {@inheritDoc}
     * @param value {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<V> put(K key, V value) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        } else if (cache.size() == capacity) {
            K firstKey = cache.keySet()
                    .iterator()
                    .next();
            cache.remove(firstKey);
        }

        cache.put(key, value);
        V putValue = cache.get(key);

        return Optional.ofNullable(putValue);
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<V> remove(K key) {
        if (!cache.containsKey(key)) {
            return Optional.empty();
        }

        V removeValue = cache.remove(key);

        return Optional.ofNullable(removeValue);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        cache.clear();
    }
}
