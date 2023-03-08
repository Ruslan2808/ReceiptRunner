package by.kantsevich.receiptrunner.cache.impl;

import by.kantsevich.receiptrunner.cache.Cache;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of the {@code Cache} interface that uses an algorithm that
 * counts the frequency of use of each element and removes those that are least
 * accessed (LFU). A {@code HashMap} is used to store key-value mappings, key-number-of-hits
 * mappings, and number-of-hits-to-set-of-associated-key mappings (stored in a
 * {@code LinkedHashSet}). To find the least frequently accessed key, the {@code minKeyFreq}
 * counter variable is used, which stores the minimum frequency of accessing cache keys.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 * @author Ruslan Kantsevich
 */
@Data
public class LFUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> keyFreq;
    private final Map<Integer, LinkedHashSet<K>> orderKeyFreq;
    private int minKeyFreq;

    /**
     * Creates an LFUCache instance with the specified capacity
     *
     * @param capacity the maximum number of entries that this cache can hold
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.keyFreq = new HashMap<>();
        this.orderKeyFreq = new HashMap<>();
        this.minKeyFreq = 0;
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

        updateFreq(key);
        V value = cache.get(key);

        return Optional.ofNullable(value);
    }

    /**
     * Associates the specified value with the specified key in this cache. If the cache
     * previously contained a mapping for the key, the old value is replaced with the
     * specified value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
     */
    @Override
    public Optional<V> put(K key, V value) {
        if (cache.containsKey(key)) {
            updateFreq(key);

            cache.put(key, value);
            V putValue = cache.get(key);

            return Optional.ofNullable(putValue);
        } else if (cache.size() == capacity) {
            var setFreq = orderKeyFreq.get(minKeyFreq);
            K leastUsedKey = setFreq.iterator().next();

            setFreq.remove(leastUsedKey);
            keyFreq.remove(leastUsedKey);
            cache.remove(leastUsedKey);
        }

        keyFreq.put(key, 1);
        orderKeyFreq.putIfAbsent(1, new LinkedHashSet<>());
        orderKeyFreq.get(1).add(key);
        minKeyFreq = 1;

        cache.put(key, value);
        V putValue = cache.get(key);

        return Optional.ofNullable(putValue);
    }

    /**
     * Removes the mapping for a key from this cache if it is present
     *
     * @param key the key whose mapping is to be removed from the cache
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
     */
    @Override
    public Optional<V> remove(K key) {
        if (!cache.containsKey(key)) {
            return Optional.empty();
        }

        int freq = keyFreq.remove(key);
        orderKeyFreq.get(freq).remove(key);
        V removeValue = cache.remove(key);

        if (freq == minKeyFreq && orderKeyFreq.get(freq).isEmpty()) {
            minKeyFreq++;
        }

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
        keyFreq.clear();
        orderKeyFreq.clear();
    }

    /**
     * Updates the frequency of use of the cache key by one
     *
     * @param key the key whose frequency of use needs to be updated
     */
    private void updateFreq(K key) {
        int freq = keyFreq.get(key);
        keyFreq.put(key, freq + 1);

        orderKeyFreq.get(freq).remove(key);
        orderKeyFreq.putIfAbsent(freq + 1, new LinkedHashSet<>());
        orderKeyFreq.get(freq + 1).add(key);

        if (freq == minKeyFreq && orderKeyFreq.get(freq).isEmpty()) {
            minKeyFreq++;
        }
    }
}
