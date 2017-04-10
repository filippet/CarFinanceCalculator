package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepositoryComponent
import com.ntangent.carfinancecalculator.util.FragmentScope
import dagger.Component

@FragmentScope
@Component(
        dependencies = arrayOf(FinanceParamsRepositoryComponent::class),
        modules = arrayOf(CalculatorPresenterModule::class)
)
interface CalculatorComponent {

    fun inject(calculatorActivity: CalculatorActivity)
}