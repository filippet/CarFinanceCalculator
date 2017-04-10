package com.ntangent.carfinancecalculator.calculator.domain

class CalculatorStringFormatterImpl: CalculatorStringFormatter {
    override fun vehiclePrice(value: Int): String {
        return value.toString()
    }

    override fun paymentAmount(value: Int): String {
        return value.toString()
    }

    override fun term(value: Int): String {
        return value.toString()
    }

    override fun rate(value: Double): String {
        return value.toString()
    }

    override fun minTermMonths(value: Int): String {
        return value.toString()
    }

    override fun maxTermMonths(value: Int): String {
        return value.toString()
    }
}