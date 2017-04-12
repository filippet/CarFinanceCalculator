package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.DefaultUseCaseObserver
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.interactor.GetVehicleLoanTermsUseCase
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class CalculatorsPresenter @Inject constructor(
        private val getLoanTermsUseCase: GetVehicleLoanTermsUseCase,
        private val view: CalcItemContract.View

): CalcItemContract.Presenter {

    var lastLoanTerms: List<FinanceParams> = arrayListOf()

    @Inject
    fun setupListeners() {
        view.setPresenter(this)
    }

    override fun subscribe() {
        retrieveLoanTerms()
    }

    override fun unsubscribe() {
        getLoanTermsUseCase.unsubscribe()
    }

    private fun retrieveLoanTerms() {
        val observer = newRetrieveLoanTermsObserver()
        getLoanTermsUseCase.execute(GetVehicleLoanTermsUseCase.params(), observer)
    }

    private fun newRetrieveLoanTermsObserver(): DisposableObserver<List<FinanceParams>> {
        return object : DefaultUseCaseObserver<List<FinanceParams>>() {
            override fun onNext(value: List<FinanceParams>) {
                lastLoanTerms = value
                view.setFinanceParamsList(lastLoanTerms)
            }
        }
    }

}