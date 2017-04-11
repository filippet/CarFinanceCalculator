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

    override fun termMonthsChanged(value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun paymentFrequencyChanged(value: PaymentFrequency) {
        if (lastLoanTerms.isNotEmpty()) {
            setView(lastLoanTerms[0])
        }
    }

    override fun cashDownAmountChanged(value: Int) {
        setView(lastLoanTerms[0])
    }

    override fun tradeInAmountChanged(value: Int) {
        setView(lastLoanTerms[0])
    }

    private fun retrieveLoanTerms() {
        val observer = newRetrieveLoanTermsObserver()
        getLoanTermsUseCase.execute(GetVehicleLoanTermsUseCase.params(), observer)
    }

    private fun newRetrieveLoanTermsObserver(): DisposableObserver<List<FinanceParams>> {
        return object : DefaultUseCaseObserver<List<FinanceParams>>() {
            override fun onNext(value: List<FinanceParams>) {
                lastLoanTerms = value
                setView(lastLoanTerms[0])
            }
        }
    }

    private fun setView(financeParams: FinanceParams) {
        val vehiclePrice = financeParams.vehiclePrice
        val termInMonths = financeParams.termRates[0].term
        val annualInterestRate = financeParams.termRates[0].rate

        val paymentFrequency = view.getPaymentFrequency()
        val cashDownAmount = view.getCashDownAmount()
        val tradeInAmount = view.getTradeInAmount()

        val payment = loanCalculator.calculateLoan(
                vehiclePrice = vehiclePrice,
                termInMonths = termInMonths,
                annualInterestRate = annualInterestRate,
                paymentFrequency = paymentFrequency,
                cashDownAmount = cashDownAmount,
                tradeInAmount = tradeInAmount
        ).payment

        with(view) {
            setVehiclePrice(stringFormatter.vehiclePrice(financeParams.vehiclePrice))
            setPaymentAmount(stringFormatter.paymentAmount(payment, paymentFrequency))
            setTerm(stringFormatter.term(termInMonths))
            setRate(stringFormatter.rate(annualInterestRate))
            setMinTermMonths(stringFormatter.minTermMonths(financeParams.minTerm()))
            setMaxTermMonths(stringFormatter.maxTermMonths(financeParams.maxTerm()))
            setPaymentFrequency(paymentFrequency)
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