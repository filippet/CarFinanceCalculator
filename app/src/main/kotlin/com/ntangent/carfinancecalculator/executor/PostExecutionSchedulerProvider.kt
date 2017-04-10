package com.ntangent.carfinancecalculator.executor

import io.reactivex.Scheduler

/**
 * Provides thread for delivering results (typically AndroidSchedulers.mainThread() on Android)
 */
interface PostExecutionSchedulerProvider {
    fun getScheduler(): Scheduler
}