package ru.sbrf.pegi18.pipeline.cache

/**
 * @author Alexey Lapin
 */
interface CacheEntryProvider<K, V> {

    V get(K key)
}
