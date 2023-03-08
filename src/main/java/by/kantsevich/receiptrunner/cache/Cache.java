package by.kantsevich.receiptrunner.cache;

import java.util.Optional;

/**
 * Interface {@code Cache} for performing operations with
 * the cache, which acts as a temporary storage of frequently
 * used objects
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 * @author Ruslan Kantsevich
 */
public interface Cache<K, V> {
    /**
     * Returns key mapping as {@code Optional} from this cache, if present
     *
     * @param key the key whose mapping is to be retrieved from the cache
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
     */
    Optional<V> get(K key);

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
    Optional<V> put(K key, V value);

    /**
     * Removes the mapping for a key from this cache if it is present
     *
     * @param key key whose mapping is to be removed from the cache
     * @return the {@code Optional} value associated with key, or {@code Optional.empty()}
     * if there was no mapping for key.
     */
    Optional<V> remove(K key);

    /**
     * Returns the number of key-value mappings in this cache.
     *
     * @return the number of key-value mappings in this cache
     */
    int size();

    /**
     * Removes all key-value mappings from this cache. The cache will be empty after this
     * call returns.
     */
    void clear();
}
