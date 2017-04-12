package com.ntangent.carfinancecalculator.data.source

import android.content.Context
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.data.executor.IoSchedulerProvider
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.UiSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FinanceParamsRepositoryModule {

    companion object {
        private val JSON_RESOURCE_FILE_ID = R.raw.android_calculator_data
    }

    @Singleton
    @Provides
    internal fun provideSubscriptionSchedulerProvider(): SubscriptionSchedulerProvider {
        return IoSchedulerProvider()
    }

    @Singleton
    @Provides
    internal fun providePostExecutionSchedulerProvider(): PostExecutionSchedulerProvider {
        return UiSchedulerProvider()
    }

    @Singleton
    @Provides
    internal fun provideResourceFileId(): Int {
        return JSON_RESOURCE_FILE_ID
    }

    @Singleton
    @Provides
    internal fun provideJsonFileReader(context: Context, resourceFileId: Int): JsonFileReader {
        return RawFileReader(context, resourceFileId)
    }

    @Singleton
    @Provides
    internal fun provideLocalFinanceParamsDataSource(jsonFileReader: JsonFileReader): FinanceParamsDataSource {
        return JsonFileFinanceParamsDataSource(jsonFileReader)
    }

    @Singleton
    @Provides
    internal fun provideFinanceParamsRepository(
            localDataSource: FinanceParamsDataSource): FinanceParamsRepository {
        return FinanceParamsRepository(localDataSource)
    }

}