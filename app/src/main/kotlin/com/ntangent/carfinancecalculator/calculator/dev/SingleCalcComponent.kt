package com.ntangent.carfinancecalculator.calculator.dev

import com.ntangent.carfinancecalculator.ApplicationModule
import com.ntangent.carfinancecalculator.calculator.dev.SingleCalcActivity
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepositoryComponent
import com.ntangent.carfinancecalculator.util.FragmentScope
import dagger.Component

@FragmentScope
@Component(
        dependencies = arrayOf(FinanceParamsRepositoryComponent::class),
        modules = arrayOf(SingleCalcPresenterModule::class, ApplicationModule::class)
)
interface SingleCalcComponent {

    fun inject(singleCalcActivity: SingleCalcActivity)
}