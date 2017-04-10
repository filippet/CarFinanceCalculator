package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.data.executor.ComputationSchedulerProvider
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.UiSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FinanceParamsRepositoryModule {

    @Singleton
    @Provides
    internal fun provideSubscriptionSchedulerProvider(): SubscriptionSchedulerProvider {
        return ComputationSchedulerProvider()
    }

    @Singleton
    @Provides
    internal fun providePostExecutionSchedulerProvider(): PostExecutionSchedulerProvider {
        return UiSchedulerProvider()
    }

    @Singleton
    @Provides
    internal fun provideLocalFinanceParamsDataSource(): FinanceParamsDataSource {
        return FakeLocalFinanceParamsDataSource()
    }

    @Singleton
    @Provides
    internal fun provideFinanceParamsRepository(
            localDataSource: FinanceParamsDataSource): FinanceParamsRepository {
        return FinanceParamsRepository(localDataSource)
    }

}