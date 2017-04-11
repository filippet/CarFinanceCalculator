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

/**
 * A Dagger module used to pass the View dependency to CalculatorPresenter
 */
@Module
class CalculatorPresenterModule(
        private val view: CalculatorContract.View ) {

    @Provides
    internal fun provideCalculatorContractView(): CalculatorContract.View {
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
    internal fun provideCalculatorPresenter(
            getLoanTermsUseCase: GetVehicleLoanTermsUseCase,
            view: CalculatorContract.View,
            loanCalculator: LoanCalculator,
            stringFormatter: CalculatorStringFormatter
    ): CalculatorContract.Presenter {
        return CalculatorPresenter(
                getLoanTermsUseCase = getLoanTermsUseCase,
                view = view,
                loanCalculator = loanCalculator,
                stringFormatter = stringFormatter)
    }
}