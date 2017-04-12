package com.ntangent.carfinancecalculator.calculator.domain

data class FinanceTermRate(
        val term: Int,
        val rate: Double
)

data class FinanceParams(
        val vehiclePrice: Int,
        val termRates: List<FinanceTermRate>
)