package by.kantsevich.receiptrunner.cache.impl;

import by.kantsevich.receiptrunner.cache.Cache;

import lombok.Data;

import java.util.*;

/**
 * An implementation of the {@code Cache} interface that uses the least recently used (LRU) algorithm,
 * where the key-value mappings that has not been used the longest is evicted from the cache.
 * {@code LinkedHashMap} is used to store key-value mappings, including their order.
 * The least recently used item is stored at the beginning of the cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
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
     * Returns key mapping as {@code Optional} from this cache, if present
     *
     * @param key the key whose mapping is to be retrieved from the cache
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
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
     * Associates the specified value with the specified key in this cache. If the cache
     * previously contained a mapping for the key, the old value is replaced with the
     * specified value.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
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
     * Removes the mapping for a key from this cache if it is present
     *
     * @param key key whose mapping is to be removed from the cache
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
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
     * Returns the number of key-value mappings in this cache.
     *
     * @return the number of key-value mappings in this cache
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * Removes all key-value mappings from this cache. The cache will be empty after this
     * call returns.
     */
    @Override
    public void clear() {
        cache.clear();
    }
}
