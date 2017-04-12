package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.data.FinanceParams
import javax.inject.Inject


class RecyclerViewPresenter(
        private val view: CalculatorContract.View,
        private val loanCalculator: LoanCalculator,
        private val stringFormatter: CalculatorStringFormatter

): CalculatorContract.Presenter {

    private lateinit var financeParams: FinanceParams

    @Inject
    fun setupListeners() {
        view.setPresenter(this)
    }

    override fun subscribe() {
//        retrieveLoanTerms()
    }

    override fun unsubscribe() {
//        getLoanTermsUseCase.unsubscribe()
    }

    override fun setFinanceParams(value: FinanceParams) {
        financeParams = value
        setView(financeParams)
    }

    override fun termMonthsChanged(value: Int) {
        setView(financeParams)
    }

    override fun paymentFrequencyChanged(value: PaymentFrequency) {
        setView(financeParams)
    }

    override fun cashDownAmountChanged(value: Int) {
        setView(financeParams)
    }

    override fun tradeInAmountChanged(value: Int) {
        setView(financeParams)
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
            setTermBounds(createTermInfo(financeParams, selectedTermIndex))
            setRate(stringFormatter.rate(annualInterestRate))
            setPaymentFrequency(paymentFrequency)
        }
    }


    //TODO: Move these methods to domain
    private fun createTermInfo(financeParams: FinanceParams, selectedTermIndex: Int): TermInfo {
        val termRange = ((financeParams.maxTerm() - financeParams.minTerm()) / 12)
        return TermInfo(
                stringFormatter.minTermMonths(financeParams.minTerm()),
                stringFormatter.maxTermMonths(financeParams.maxTerm()),
                termRange,
                selectedTermIndex)
    }

    private fun FinanceParams.minTerm(): Int {
        return termRates.first().term
    }

    private fun FinanceParams.maxTerm(): Int {
        return termRates.last().term
    }
}