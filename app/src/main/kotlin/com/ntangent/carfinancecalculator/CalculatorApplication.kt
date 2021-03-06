package com.ntangent.carfinancecalculator

import android.app.Application
import com.ntangent.carfinancecalculator.data.source.DaggerFinanceParamsRepositoryComponent
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepositoryComponent

class CalculatorApplication: Application() {

    private lateinit var repositoryComponent: FinanceParamsRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    fun getFinanceParamsRepositoryComponent() = repositoryComponent

    private fun initializeInjector() {
        repositoryComponent = DaggerFinanceParamsRepositoryComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }
}