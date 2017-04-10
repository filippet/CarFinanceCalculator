package com.ntangent.carfinancecalculator.testutil

import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Provides scheduler on the same (calling) thread
 */
class TestPostExecutionSchedulerProvider : PostExecutionSchedulerProvider {
    override fun getScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}
