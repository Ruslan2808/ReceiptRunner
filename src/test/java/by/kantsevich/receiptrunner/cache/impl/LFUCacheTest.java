package by.kantsevich.receiptrunner.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {

    private LFUCache<Integer, String> lfuCache;

    @BeforeEach
    void setUp() {
        lfuCache = new LFUCache<>(3);
    }

    @Test
    void checkGetShouldReturnString() {
        Integer key = 1;
        String expectedString = "One";

        lfuCache.put(key, expectedString);

        Optional<String> actualString = lfuCache.get(key);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkGetShouldReturnNullString() {
        lfuCache.put(1, "One");
        lfuCache.put(3, "Three");
        lfuCache.put(2, "Two");
        lfuCache.put(4, "Four");

        Optional<String> actualString = lfuCache.get(1);

        assertThat(actualString).isNotPresent();
    }

    @Test
    void checkPutShouldReturnString() {
        String expectedString = "One";

        Optional<String> actualString = lfuCache.put(1, expectedString);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkPutShouldReturnCache() {
        lfuCache.put(1, "One");
        lfuCache.put(3, "Three");
        lfuCache.put(2, "Two");
        lfuCache.get(3);
        lfuCache.put(4, "Four");
        lfuCache.put(5, "Five");
        lfuCache.get(4);
        lfuCache.remove(4);
        lfuCache.put(6, "Six");

        assertAll(
                () -> assertThat(lfuCache.getCache().keySet()).contains(3, 5, 6),
                () -> assertThat(lfuCache.getCache().values()).contains("Three", "Five", "Six")
        );
    }

    @Test
    void checkPutShouldReturnOrderedKeyFreqCache() {
        lfuCache.put(1, "One");
        lfuCache.put(3, "Three");
        lfuCache.put(2, "Two");
        lfuCache.get(3);
        lfuCache.put(4, "Four");
        lfuCache.put(5, "Five");
        lfuCache.get(4);

        assertAll(
                () -> assertThat(lfuCache.getOrderKeyFreq().keySet()).containsExactly(1, 2),
                () -> assertThat(lfuCache.getOrderKeyFreq().get(1)).contains(5),
                () -> assertThat(lfuCache.getOrderKeyFreq().get(2)).contains(3, 4)
        );
    }

    @Test
    void checkRemoveShouldReturnString() {
        Integer key = 1;
        String expectedString = "One";

        lfuCache.put(key, expectedString);

        Optional<String> actualString = lfuCache.remove(key);

        assertThat(actualString).isPresent();
        assertThat(actualString.get()).isEqualTo(expectedString);
    }

    @Test
    void checkRemoveShouldReturnNullString() {
        lfuCache.put(1, "One");

        Optional<String> actualString = lfuCache.remove(2);

        assertThat(actualString).isNotPresent();
    }

    @Test
    void checkRemoveShouldReturnMinKeyFreq2() {
        int expectedMinKeyFreq = 2;

        lfuCache.put(1, "One");
        lfuCache.put(3, "Three");
        lfuCache.put(2, "Two");
        lfuCache.get(3);
        lfuCache.put(4, "Four");
        lfuCache.put(5, "Five");
        lfuCache.get(4);

        lfuCache.remove(5);
        int actualMinKeyFreq = lfuCache.getMinKeyFreq();

        assertThat(actualMinKeyFreq).isEqualTo(expectedMinKeyFreq);
    }

    @Test
    void checkSizeShouldReturn2() {
        int expectedSize = 2;

        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");

        int actualSize = lfuCache.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void checkSizeShouldReturnSize3() {
        int expectedSize = 3;

        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");
        lfuCache.put(3, "Three");
        lfuCache.put(4, "Four");

        int actualSize = lfuCache.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void checkClearShouldReturnSize0() {
        lfuCache.put(1, "One");
        lfuCache.put(2, "Two");

        lfuCache.clear();
        int actualSize = lfuCache.size();

        assertThat(actualSize).isZero();
    }
}
