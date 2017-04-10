package com.ntangent.carfinancecalculator.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class UiSchedulerProvider : PostExecutionSchedulerProvider {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}