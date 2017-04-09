package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.BasePresenter
import com.ntangent.carfinancecalculator.BaseView

enum class PaymentFrequency {
    MONTHLY,
    BI_WEEKLY,
    WEEKLY
}

class CalculatorContract {

    interface Presenter: BasePresenter {
        fun termMonthsChanged(value: Int)

        fun cashDownAmountChanged(value: Double)

        fun tradeInAmountChanged(value: Double)
    }

    interface View: BaseView<Presenter> {
        fun setVehiclePrice(value: String)

        fun setPaymentAmount(value: String)

        fun setTerm(value: String)

        fun setRate(value: String)

        fun setMinTermMonths(value: String)

        fun setMaxTermMonths(value: String)

        fun setPaymentFrequency(value: PaymentFrequency)
    }
}