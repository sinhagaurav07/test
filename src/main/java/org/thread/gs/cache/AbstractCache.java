package org.thread.gs.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> implements Cache<K, V>{

    @Override
    public Map<K, V> get(Iterable<?> keys) {
        Map<K, V> map = new LinkedHashMap<>();
        for(Object key : keys){
            V value = get((K)key);
            map.put((K)key, value);
        }
        return map;
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidate(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidateAll(Iterable<?> keys) {
        for(Object key : keys) {
            invalidate((K)key);
        }
    }
}
