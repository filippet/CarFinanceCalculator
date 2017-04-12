package com.ntangent.carfinancecalculator.data.executor

import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class IoSchedulerProvider : SubscriptionSchedulerProvider {
    override fun getScheduler(): Scheduler {
        return Schedulers.io()
    }
}
