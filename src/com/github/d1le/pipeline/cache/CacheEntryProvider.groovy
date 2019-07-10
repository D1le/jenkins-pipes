package com.github.d1le.pipeline.cache

/**
 * @author Alexey Lapin
 */
interface CacheEntryProvider<K, V> {

    V get(K key)
}
