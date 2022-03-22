package org.thread.gs.cache;

public class CacheBuilder<K, V> {

    <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(){
        return new LocalCache.LocalLoadingCache<K1, V1>();
    }

    static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<Object, Object>();
    }
}
