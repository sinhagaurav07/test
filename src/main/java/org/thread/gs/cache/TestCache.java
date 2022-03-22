package org.thread.gs.cache;

public class TestCache {

    public static void main(String[] args) {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build();
        loadingCache.put("Hello", "hello Uber");
        String result = loadingCache.get("Hello");
        System.out.println(result);
        loadingCache.invalidate("Hello");
        result = loadingCache.get("Hello");
        System.out.println(result);
    }
}
