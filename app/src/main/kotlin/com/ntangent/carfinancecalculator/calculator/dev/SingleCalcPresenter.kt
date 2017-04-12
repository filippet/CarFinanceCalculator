package com.ntangent.carfinancecalculator.calculator.dev

import com.ntangent.carfinancecalculator.DefaultUseCaseObserver
import com.ntangent.carfinancecalculator.calculator.CalculatorContract
import com.ntangent.carfinancecalculator.calculator.TermInfoViewModel
import com.ntangent.carfinancecalculator.calculator.createTermInfo
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.calculator.domain.interactor.GetVehicleLoanTermsUseCase
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class SingleCalcPresenter @Inject constructor (
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

    override fun setFinanceParams(value: FinanceParams) {
        //Not needed here
    }

    override fun termMonthsChanged(value: Int) {
        if (lastLoanTerms.isNotEmpty()) {
            setView(lastLoanTerms[0])
        }
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

        val selectedTermIndex = view.getSelectedTermIndex()
        val annualInterestRate = financeParams.termRates[selectedTermIndex].rate
        val termInMonths = financeParams.termRates[selectedTermIndex].term

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
            setTermBounds(createTermInfo(financeParams, selectedTermIndex, stringFormatter))
            setRate(stringFormatter.rate(annualInterestRate))
            setPaymentFrequency(paymentFrequency)
        }
    }
}