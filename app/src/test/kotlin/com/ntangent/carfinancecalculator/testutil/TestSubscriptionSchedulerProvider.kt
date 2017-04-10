package com.ntangent.carfinancecalculator.testutil

import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Provides scheduler on the same (calling) thread
 */
class TestSubscriptionSchedulerProvider : SubscriptionSchedulerProvider {
    override fun getScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}
