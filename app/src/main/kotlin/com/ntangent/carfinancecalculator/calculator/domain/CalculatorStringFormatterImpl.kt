package com.ntangent.carfinancecalculator.calculator.domain

import android.content.Context
import android.content.res.Resources
import com.ntangent.carfinancecalculator.R
import java.text.NumberFormat
import javax.inject.Inject

class CalculatorStringFormatterImpl @Inject constructor(context: Context): CalculatorStringFormatter {

    private val currencyFormat = NumberFormat.getCurrencyInstance()
    private val percentFormat = NumberFormat.getPercentInstance()

    private var res: Resources

    init {
        currencyFormat.isParseIntegerOnly = true
        currencyFormat.minimumFractionDigits = 0
        currencyFormat.maximumFractionDigits = 0

        percentFormat.minimumFractionDigits = 2
        percentFormat.maximumFractionDigits = 2

        res = context.resources
    }

    override fun vehiclePrice(value: Int): String {
        return toCurrency(value)
    }

    override fun paymentAmount(value: Int, paymentFrequency: PaymentFrequency): String {
        val amount = toCurrency(value)
        val paymentFrequencyName = loadPaymentFrequencyName(paymentFrequency)
        return res.getString(R.string.payment_amount_template, amount, paymentFrequencyName)
    }

    private fun loadPaymentFrequencyName(paymentFrequency: PaymentFrequency): String {
        val resId = when(paymentFrequency) {
            PaymentFrequency.MONTHLY   -> R.string.monthly
            PaymentFrequency.BI_WEEKLY -> R.string.bi_weekly
            PaymentFrequency.WEEKLY    -> R.string.weekly
        }
        return res.getString(resId)
    }
    override fun term(value: Int): String {
        val amount = value.toString()
        val months = res.getString(R.string.months)
        return res.getString(R.string.payment_term_template, amount, months)
    }

    override fun rate(value: Double): String {
        val amount = toPercent(value)
        val financing = res.getString(R.string.financing)
        return res.getString(R.string.payment_rate_template, amount, financing)
    }

    override fun minTermMonths(value: Int): String {
        return termMonths(value)
    }

    override fun maxTermMonths(value: Int): String {
        return termMonths(value)
    }

    private fun termMonths(value: Int): String {
        val amount = value.toString()
        val monthAbbreviated = res.getString(R.string.month_abbreviated)
        return res.getString(R.string.payment_term_months_template, amount, monthAbbreviated)
    }

    private fun toCurrency(value: Int): String {
        return currencyFormat.format(value)
    }

    private fun toPercent(value: Double): String {
        return percentFormat.format(value / 100)
    }
}