package com.ts.archspike.common.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineDispatcherProvider {
    open val default: CoroutineContext = Dispatchers.Default
    open val ui: CoroutineContext = Dispatchers.Main
    open val io: CoroutineContext = Dispatchers.IO
}