package com.ntangent.carfinancecalculator.calculator.domain

/**
 * Thrown when the total down payment (= cash down + trade in)
 * is equal or greater than the vehicle price
 */
class NoLoanRequiredException(message: String): Exception(message)