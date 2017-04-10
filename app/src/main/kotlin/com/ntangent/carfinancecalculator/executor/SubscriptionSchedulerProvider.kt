package com.ntangent.carfinancecalculator.executor

import io.reactivex.Scheduler

/**
 * Provides scheduler for the subscription thread.
 */
interface SubscriptionSchedulerProvider {
    fun  getScheduler(): Scheduler
}