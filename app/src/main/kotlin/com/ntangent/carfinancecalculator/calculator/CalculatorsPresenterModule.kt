package com.ntangent.carfinancecalculator.calculator

import android.content.Context
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatterImpl
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.interactor.GetVehicleLoanTermsUseCase
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepository
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class CalculatorsPresenterModule(
        private val view: CalcItemContract.View ) {

    @Provides
    internal fun provideRecyclerViewContractView(): CalcItemContract.View {
        return view
    }

    @Provides
    internal fun provideGetVehicleLoanTermsUseCase(
            financeParamsRepository: FinanceParamsRepository,
            subscriptionSchedulerProvider: SubscriptionSchedulerProvider,
            postExecutionSchedulerProvider: PostExecutionSchedulerProvider
    ): GetVehicleLoanTermsUseCase {
        return GetVehicleLoanTermsUseCase(financeParamsRepository,
                subscriptionSchedulerProvider, postExecutionSchedulerProvider)
    }

    @Provides
    internal fun provideLoanCalculator(): LoanCalculator {
        return LoanCalculator()
    }

    @Provides
    internal fun provideCalculatorStringFormatter(context: Context): CalculatorStringFormatter {
        return CalculatorStringFormatterImpl(context)
    }

    @Provides
    internal fun providesRecyclerViewContractPresenter(
            getLoanTermsUseCase: GetVehicleLoanTermsUseCase,
            view: CalcItemContract.View
    ): CalcItemContract.Presenter {
        return CalculatorsPresenter(getLoanTermsUseCase, view)
    }
}