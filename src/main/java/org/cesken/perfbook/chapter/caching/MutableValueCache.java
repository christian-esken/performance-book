package org.cesken.perfbook.chapter.caching;

import com.trivago.triava.tcache.TCacheFactory;
import com.trivago.triava.tcache.core.Builder;
import org.cesken.perfbook.util.ConcurrentUtil;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.LinkedList;

/**
 * Example for mutable data stored in a cache
 */
public class MutableValueCache {
    static class IntegerList extends LinkedList<Integer> {};

    public static void main(String[] args) {
        int key = 1;
        Cache<Integer, IntegerList> cache = createCache("MutableValueDemo", key);
        Thread thread = startReadThread(cache, key);

        IntegerList integerList = cache.get(key);
        integerList.add(2);
        integerList.add(40);
        ConcurrentUtil.sleepInterruptably(10);

        for (int value = 0; value <= Integer.MAX_VALUE; value++) {
            integerList = cache.get(key);
            integerList.add(value);
            if (!thread.isAlive()) {
                System.out.println("Read thread not alive anymore ... exiting");
                break;
            }
        }
    }

    /**
     * Creates and starts a thread that iterates the LinkedList stored in the Cache under the given key
     * @param cache
     * @param key
     */
    private static Thread startReadThread(Cache<Integer, IntegerList> cache, int key) {
        Runnable readListRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    IntegerList linkedList = cache.get(key);
                    int sum = 0;
                    for (Integer intValue : linkedList) {
                        sum += intValue;
                    }
                    System.out.println("Sum: " + sum);
                }
            }
        };
        Thread thread = new Thread(readListRunnable);
        thread.start();
        return thread;
    }

    /**
     * Creates a Cache via JSR107 API,.
     *
     * @param cacheName Cache name
     * @param key
     * @return The Cache
     */
    static javax.cache.Cache<Integer, IntegerList> createCache(String cacheName, int key) {
        // Build JSR107 Cache instance
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        Builder<Integer, IntegerList> builder = TCacheFactory.standardFactory().builder();
        Cache<Integer, IntegerList> cache = cacheManager.createCache(cacheName, builder); // Build
        cache.put(key, new IntegerList());
        return cache;
    }
}
