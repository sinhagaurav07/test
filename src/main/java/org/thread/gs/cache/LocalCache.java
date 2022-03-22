package org.thread.gs.cache;

import java.util.concurrent.ConcurrentHashMap;

public class LocalCache {

    static private ConcurrentHashMap map = new ConcurrentHashMap();

    static class LocalLoadingCache<K, V> extends AbstractLoadingCache<K, V> {

        @Override
        public void put(K key, V value) {
            map.put(key, value);
        }

        @Override
        public V get(K key) {
            return (V)map.get(key);
        }

        @Override
        public void invalidate(K key){
            map.remove(key);
        }
    }
}
