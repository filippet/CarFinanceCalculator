package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.data.FinanceParams


class CalculatorPresenter(
        private val financeParams: FinanceParams,
        private val view: CalculatorContract.View,
        private val loanCalculator: LoanCalculator,
        private val stringFormatter: CalculatorStringFormatter
): CalculatorContract.Presenter {

    override fun subscribe() {
        setView()
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    private fun setView() {
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


        view.setVehiclePrice(stringFormatter.vehiclePrice(financeParams.vehiclePrice))
        view.setPaymentAmount(stringFormatter.paymentAmount(payment))
        view.setTerm(stringFormatter.term(termInMonths))
        view.setRate(stringFormatter.rate(annualInterestRate))
        view.setMinTermMonths(stringFormatter.minTermMonths(12))
        view.setMaxTermMonths(stringFormatter.maxTermMonths(24))
        view.setPaymentFrequency(PaymentFrequency.MONTHLY)
    }
}