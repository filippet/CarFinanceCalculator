package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.BasePresenter
import com.ntangent.carfinancecalculator.BaseView
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency

class CalculatorContract {

    interface Presenter: BasePresenter {
        fun termMonthsChanged(value: Int)

        fun cashDownAmountChanged(value: Int)

        fun tradeInAmountChanged(value: Int)
    }

    interface View: BaseView<Presenter> {
        fun setVehiclePrice(value: String)

        fun setPaymentAmount(value: String)

        fun setTerm(value: String)

        fun setRate(value: String)

        fun setMinTermMonths(value: String)

        fun setMaxTermMonths(value: String)

        fun setPaymentFrequency(value: PaymentFrequency)

        fun getCashDownAmount(): Int

        fun getTradeInAmount(): Int
    }
}