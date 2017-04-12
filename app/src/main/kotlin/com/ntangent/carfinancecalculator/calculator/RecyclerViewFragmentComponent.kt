package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.ApplicationModule
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepositoryComponent
import com.ntangent.carfinancecalculator.util.FragmentScope
import dagger.Component

@FragmentScope
@Component(
        dependencies = arrayOf(FinanceParamsRepositoryComponent::class),
        modules = arrayOf(RecyclerViewFragmentPresenterModule::class, ApplicationModule::class)
)
interface RecyclerViewFragmentComponent {

    fun inject(recyclerViewActivity: RecyclerViewActivity)
}