package com.github.d1le.pipeline.cache

/**
 * @author Alexey Lapin
 */
class Cache<K, V> implements Serializable {

    Map<K, V> map = new HashMap()
    CacheEntryProvider<K, V> provider

    V get(K key) {
        V value = map.get(key)
        if(value == null && provider != null) {
            value = provider.get(key)
            put(key, value)
        }
        value
    }

    void put(K key, V value) {
        map.put(key, value)
    }

    void clear() {
        map.clear()
    }
}
