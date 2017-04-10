package com.ntangent.carfinancecalculator

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * This Dagger module is used to pass in the Context dependency to FinanceParamsRepositoryComponent
 */
@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }
}
