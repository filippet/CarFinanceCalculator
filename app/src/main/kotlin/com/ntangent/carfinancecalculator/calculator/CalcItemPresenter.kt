package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.*
import javax.inject.Inject


class CalcItemPresenter(
        private val view: CalculatorContract.View,
        private val loanCalculator: LoanCalculator,
        private val stringFormatter: CalculatorStringFormatter

): CalculatorContract.Presenter {

    private var financeParams: FinanceParams? = null

    @Inject
    fun setupListeners() {
        view.setPresenter(this)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun setFinanceParams(value: FinanceParams) {
        financeParams = value
        setView()
    }

    override fun termMonthsChanged(value: Int) {
        setView()
    }

    override fun paymentFrequencyChanged(value: PaymentFrequency) {
        setView()
    }

    override fun cashDownAmountChanged(value: Int) {
        setView()
    }

    override fun tradeInAmountChanged(value: Int) {
        setView()
    }

    private fun setView() {
        if (financeParams == null) return
        val vehiclePrice = financeParams!!.vehiclePrice

        val selectedTermIndex = view.getSelectedTermIndex()
        val annualInterestRate = financeParams!!.termRates[selectedTermIndex].rate
        val termInMonths = financeParams!!.termRates[selectedTermIndex].term

        val paymentFrequency = view.getPaymentFrequency()
        val cashDownAmount = view.getCashDownAmount()
        val tradeInAmount = view.getTradeInAmount()

        val payment = calculatePayment(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = annualInterestRate,
                    paymentFrequency = paymentFrequency,
                    cashDownAmount = cashDownAmount,
                    tradeInAmount = tradeInAmount)

        with(view) {
            setVehiclePrice(stringFormatter.vehiclePrice(financeParams!!.vehiclePrice))
            setPaymentAmount(stringFormatter.paymentAmount(payment, paymentFrequency))
            setTerm(stringFormatter.term(termInMonths))
            setTermBounds(createTermInfo(financeParams!!, selectedTermIndex, stringFormatter))
            setRate(stringFormatter.rate(annualInterestRate))
            setPaymentFrequency(paymentFrequency)
        }
    }

    fun calculatePayment(
            vehiclePrice: Int,
            termInMonths: Int,
            annualInterestRate: Double,
            paymentFrequency: PaymentFrequency,
            cashDownAmount: Int,
            tradeInAmount: Int): Int {

        return try {
            loanCalculator.calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = annualInterestRate,
                    paymentFrequency = paymentFrequency,
                    cashDownAmount = cashDownAmount,
                    tradeInAmount = tradeInAmount
            ).payment
        } catch (e: NoLoanRequiredException) {
            0
        }
    }
}