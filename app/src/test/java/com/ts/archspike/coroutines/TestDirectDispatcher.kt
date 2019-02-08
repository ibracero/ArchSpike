package com.touchsurgery.data.mockHelper.coroutine

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

@UseExperimental(InternalCoroutinesApi::class)
class TestDirectDispatcher : CoroutineDispatcher(), Delay {
    override fun scheduleResumeAfterDelay(time: Long, continuation: CancellableContinuation<Unit>) {
        continuation.resume(Unit)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }
}
