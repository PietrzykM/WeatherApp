package edu.psmw.weatherapp.internal

import android.provider.Settings
import kotlinx.coroutines.*

fun <T> lazyDefferd(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}