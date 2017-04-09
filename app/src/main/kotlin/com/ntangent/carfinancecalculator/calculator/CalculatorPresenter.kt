package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.data.FinanceParams


class CalculatorPresenter(
        private val financeParams: FinanceParams,
        private val view: CalculatorContract.View
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
        view.setVehiclePrice(financeParams.vehiclePrice.toString())
        view.setPaymentAmount(25.toString())
        view.setTerm(financeParams.termRates[0].term.toString())
        view.setRate(financeParams.termRates[0].rate.toString())
        view.setMinTermMonths(1.toString())
        view.setMaxTermMonths(12.toString())
        view.setPaymentFrequency(PaymentFrequency.MONTHLY)
    }
}