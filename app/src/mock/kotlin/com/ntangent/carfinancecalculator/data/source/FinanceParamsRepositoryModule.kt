package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.data.executor.ComputationSchedulerProvider
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.UiSchedulerProvider
import com.ntangent.carfinancecalculator.model.GsonVehicleTermParamsJsonDeserializer
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
    internal fun provideVehicleTermParamsJsonDeserializer(): VehicleTermParamsJsonDeserializer {
        return GsonVehicleTermParamsJsonDeserializer()
    }

    @Singleton
    @Provides
    internal fun provideLocalFinanceParamsDataSource(
            jsonDeserializer: VehicleTermParamsJsonDeserializer
    ): FinanceParamsDataSource {
        return FakeLocalFinanceParamsDataSource(jsonDeserializer)
    }

    @Singleton
    @Provides
    internal fun provideFinanceParamsRepository(
            localDataSource: FinanceParamsDataSource): FinanceParamsRepository {
        return FinanceParamsRepository(localDataSource)
    }

}