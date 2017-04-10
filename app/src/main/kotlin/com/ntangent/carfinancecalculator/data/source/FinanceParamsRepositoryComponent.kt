package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.ApplicationModule
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (
        modules = arrayOf(FinanceParamsRepositoryModule::class, ApplicationModule::class)
)
interface FinanceParamsRepositoryComponent {

    fun financeParamsRepository(): FinanceParamsRepository
    fun subscriptionSchedulerProvider(): SubscriptionSchedulerProvider
    fun postExecutionSchedulerProvider(): PostExecutionSchedulerProvider
}