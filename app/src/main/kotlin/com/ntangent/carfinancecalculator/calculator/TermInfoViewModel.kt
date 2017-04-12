package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams

data class TermInfoViewModel(
        val minTerm: String,
        val maxTerm: String,
        val termRange: Int,
        val selectedTerm: Int
)

fun createTermInfo(
        financeParams: FinanceParams,
        selectedTermIndex: Int,
        stringFormatter: CalculatorStringFormatter): TermInfoViewModel {

    val termRange = ((financeParams.maxTerm() - financeParams.minTerm()) / 12)
    return TermInfoViewModel(
            stringFormatter.minTermMonths(financeParams.minTerm()),
            stringFormatter.maxTermMonths(financeParams.maxTerm()),
            termRange,
            selectedTermIndex)
}

fun FinanceParams.minTerm(): Int {
    return termRates.first().term
}

fun FinanceParams.maxTerm(): Int {
    return termRates.last().term
}
