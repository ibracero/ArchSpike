package com.ts.archspike.coroutines

import com.touchsurgery.data.mockHelper.coroutine.TestDirectDispatcher
import com.ts.archspike.common.coroutines.CoroutineDispatcherProvider
import kotlin.coroutines.CoroutineContext

class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider() {
    override val default: CoroutineContext = TestDirectDispatcher()
    override val ui: CoroutineContext = TestDirectDispatcher()
    override val io: CoroutineContext = TestDirectDispatcher()
}
