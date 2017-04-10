package com.ntangent.carfinancecalculator.data.executor

import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ComputationSchedulerProvider : SubscriptionSchedulerProvider {
    override fun getScheduler(): Scheduler {
        return Schedulers.computation()
    }
}
