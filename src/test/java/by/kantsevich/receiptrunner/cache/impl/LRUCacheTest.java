package by.kantsevich.receiptrunner.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LRUCacheTest {

    private LRUCache<Integer, String> lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LRUCache<>(3);
    }

    @Test
    void checkGetShouldReturnString() {
        Integer key = 1;
        String expectedString = "One";

        lruCache.put(key, expectedString);

        Optional<String> actualString = lruCache.get(key);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkGetShouldReturnNullString() {
        lruCache.put(1, "One");
        lruCache.put(3, "Three");
        lruCache.put(2, "Two");
        lruCache.put(4, "Four");

        Optional<String> actualString = lruCache.get(1);

        assertThat(actualString).isNotPresent();
    }

    @Test
    void checkPutShouldReturnString() {
        String expectedString = "One";

        Optional<String> actualString = lruCache.put(1, expectedString);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkPutShouldReturnOrderedCache() {
        lruCache.put(1, "One");
        lruCache.put(3, "Three");
        lruCache.put(2, "Two");
        lruCache.get(3);
        lruCache.put(4, "Four");
        lruCache.put(5, "Five");
        lruCache.get(4);

        assertAll(
                () -> assertThat(lruCache.getCache().keySet()).containsExactly(3, 5, 4),
                () -> assertThat(lruCache.getCache().values()).containsExactly("Three", "Five", "Four")
        );
    }

    @Test
    void checkRemoveShouldReturnString() {
        Integer key = 1;
        String expectedString = "One";

        lruCache.put(key, expectedString);

        Optional<String> actualString = lruCache.remove(key);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkRemoveShouldReturnNullString() {
        lruCache.put(1, "One");

        Optional<String> actualString = lruCache.remove(2);

        assertThat(actualString).isNotPresent();
    }

    @Test
    void checkSizeShouldReturn2() {
        int expectedSize = 2;

        lruCache.put(1, "One");
        lruCache.put(2, "Two");

        int actualSize = lruCache.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void checkSizeShouldReturnSize3() {
        int expectedSize = 3;

        lruCache.put(1, "One");
        lruCache.put(2, "Two");
        lruCache.put(3, "Three");
        lruCache.put(4, "Four");

        int actualSize = lruCache.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void checkClearShouldReturnSize0() {
        lruCache.put(1, "One");
        lruCache.put(2, "Two");

        lruCache.clear();
        int actualSize = lruCache.size();

        assertThat(actualSize).isZero();
    }
}
