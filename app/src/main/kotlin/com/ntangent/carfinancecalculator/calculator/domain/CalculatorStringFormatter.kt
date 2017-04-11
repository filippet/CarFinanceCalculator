package com.ntangent.carfinancecalculator.calculator.domain

interface CalculatorStringFormatter {
    fun vehiclePrice(value: Int): String

    fun paymentAmount(value: Int, paymentFrequency: PaymentFrequency): String

    fun term(value: Int): String

    fun rate(value: Double): String

    fun minTermMonths(value: Int): String

    fun maxTermMonths(value: Int): String
}