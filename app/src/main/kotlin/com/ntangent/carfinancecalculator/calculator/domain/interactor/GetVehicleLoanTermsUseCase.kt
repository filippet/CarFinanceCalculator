package com.ntangent.carfinancecalculator.calculator.domain.interactor

import com.ntangent.carfinancecalculator.ObservableUseCase
import com.ntangent.carfinancecalculator.data.FinanceParams
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepository
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Observable

/**
 * Use-case for fetching parameters for describing various car loans policies.
 */
class GetVehicleLoanTermsUseCase(
        private val financeParamsRepository: FinanceParamsRepository,
        subscriptionSchedulerProvider: SubscriptionSchedulerProvider,
        postExecutionSchedulerProvider: PostExecutionSchedulerProvider

): ObservableUseCase<List<FinanceParams>, GetVehicleLoanTermsUseCase.Params>(subscriptionSchedulerProvider, postExecutionSchedulerProvider) {

    class Params(val unused: String)

    companion object {
        fun params() = Params("unused")
    }

    override fun buildObservable(params: Params): Observable<List<FinanceParams>> {
        return financeParamsRepository.getFinanceParams()
    }
}