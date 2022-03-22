package org.thread.gs.cache;

import java.util.Map;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    Map<K, V> get(Iterable<?> keys);

    void invalidate(K key);

    void invalidateAll(Iterable<?> keys);
}
