package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.DefaultUseCaseObserver
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.calculator.domain.interactor.GetVehicleLoanTermsUseCase
import com.ntangent.carfinancecalculator.data.FinanceParams
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class CalculatorPresenter @Inject constructor (
        private val getLoanTermsUseCase: GetVehicleLoanTermsUseCase,
        private val view: CalculatorContract.View,
        private val loanCalculator: LoanCalculator,
        private val stringFormatter: CalculatorStringFormatter

): CalculatorContract.Presenter {

    //TODO: move this to domain
    val DEFAULT_PAYMENT_FREQUENCY = PaymentFrequency.MONTHLY

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

    override fun termMonthsChanged(value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cashDownAmountChanged(value: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun tradeInAmountChanged(value: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun retrieveLoanTerms() {
        val observer = newRetrieveLoanTermsObserver()
        getLoanTermsUseCase.execute(GetVehicleLoanTermsUseCase.params(), observer)
    }

    private fun newRetrieveLoanTermsObserver(): DisposableObserver<List<FinanceParams>> {
        return object : DefaultUseCaseObserver<List<FinanceParams>>() {
            override fun onNext(value: List<FinanceParams>) {
                setView(value[0])
            }
        }
    }

    private fun setView(financeParams: FinanceParams) {
        val vehiclePrice = financeParams.vehiclePrice
        val termInMonths = financeParams.termRates[0].term
        val annualInterestRate = financeParams.termRates[0].rate
        val paymentFrequency = PaymentFrequency.MONTHLY

        val payment = loanCalculator.calculateLoan(
                vehiclePrice = vehiclePrice,
                termInMonths = termInMonths,
                annualInterestRate = annualInterestRate,
                paymentFrequency = paymentFrequency
        ).payment

        with(view) {
            setVehiclePrice(stringFormatter.vehiclePrice(financeParams.vehiclePrice))
            setPaymentAmount(stringFormatter.paymentAmount(payment, DEFAULT_PAYMENT_FREQUENCY))
            setTerm(stringFormatter.term(termInMonths))
            setRate(stringFormatter.rate(annualInterestRate))
            setMinTermMonths(stringFormatter.minTermMonths(financeParams.minTerm()))
            setMaxTermMonths(stringFormatter.maxTermMonths(financeParams.maxTerm()))
            setPaymentFrequency(DEFAULT_PAYMENT_FREQUENCY)
        }
    }


    //TODO: Move these methods to domain
    private fun FinanceParams.minTerm(): Int {
        return termRates.first().term
    }

    private fun FinanceParams.maxTerm(): Int {
        return termRates.last().term
    }
}