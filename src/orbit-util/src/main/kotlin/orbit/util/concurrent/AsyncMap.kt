/*
 Copyright (C) 2015 - 2019 Electronic Arts Inc.  All rights reserved.
 This file is part of the Orbit Project <https://www.orbit.cloud>.
 See license in LICENSE.
 */

package orbit.util.concurrent

interface AsyncMap<K, V> {
    suspend fun set(key: K, value: V)
    suspend fun get(key: K): V?
    suspend fun getValue(key: K): V = get(key)!!

    suspend fun remove(key: K): Boolean
    suspend fun compareAndSet(key: K, initialValue: V?, newValue: V?): Boolean

    suspend fun entries(): Iterable<Pair<K, V>>
    suspend fun values() = entries().map { (_, v) -> v }
    suspend fun count() = entries().count()

    suspend fun getOrPut(key: K, block: suspend () -> V): V {
        val initial = get(key)
        if (initial != null) return initial

        val computed = block()

        return if (compareAndSet(key, null, computed)) {
            computed
        } else {
            getValue(key)

        }
    }

    suspend fun manipulate(key: K, block: (V?) -> V?): V? = manipulateRecursive(key, block)
}

private suspend fun <K, V> AsyncMap<K, V>.manipulateRecursive(key: K, block: (V?) -> V?): V? {
    val initialValue = this.get(key)
    val newValue = block(initialValue)
    return if (this.compareAndSet(key, initialValue, newValue)) {
        newValue
    } else {
        manipulate(key, block)
    }
}