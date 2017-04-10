package com.ntangent.carfinancecalculator.calculator.domain

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

/**
 * The expected amounts for the tests in this file are obtained by using AutoTRADER's
 * loan calculator at http://wwwa.autotrader.ca/finance/calculator
 *
 */
class LoanCalculatorTest {

    companion object {
        private val VEHICLE_PRICE = 20000
        private val ANNUAL_INTEREST_RATE = 10.0
    }

    @Test fun twoYearLoan_Monthly() {
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        assertEquals(LoanCalculator.Result(payment = 923, costOfBorrowing = 2150),
                LoanCalculator().calculateLoan(VEHICLE_PRICE, termInMonths, ANNUAL_INTEREST_RATE, paymentFrequency))
    }

    @Test fun twoYearLoan_BiWeekly() {
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.BI_WEEKLY

        assertEquals(LoanCalculator.Result(payment = 425, costOfBorrowing = 2105),
                LoanCalculator().calculateLoan(VEHICLE_PRICE, termInMonths, ANNUAL_INTEREST_RATE, paymentFrequency))
    }

    @Test fun sixYearLoan_BiWeekly() {
        val termInMonths = 6 * 12
        val paymentFrequency = PaymentFrequency.BI_WEEKLY

        assertEquals(LoanCalculator.Result(payment = 171, costOfBorrowing = 6634),
                LoanCalculator().calculateLoan(VEHICLE_PRICE, termInMonths, ANNUAL_INTEREST_RATE, paymentFrequency))
    }

    @Test fun twoYearLoan_Monthly_with_cashDown() {
        val vehiclePrice = 30000
        val cashDownAmount = 10000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        assertEquals(LoanCalculator.Result(payment = 923, costOfBorrowing = 2150),
                LoanCalculator().calculateLoan(
                        vehiclePrice = vehiclePrice,
                        termInMonths = termInMonths,
                        annualInterestRate = ANNUAL_INTEREST_RATE,
                        paymentFrequency = paymentFrequency,
                        cashDownAmount = cashDownAmount))
    }

    @Test fun twoYearLoan_Monthly_with_tradeIn() {
        val vehiclePrice = 30000
        val tradeInAmount = 10000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        assertEquals(LoanCalculator.Result(payment = 923, costOfBorrowing = 2150),
                LoanCalculator().calculateLoan(
                        vehiclePrice = vehiclePrice,
                        termInMonths = termInMonths,
                        annualInterestRate = ANNUAL_INTEREST_RATE,
                        paymentFrequency = paymentFrequency,
                        tradeInAmount = tradeInAmount))
    }

    @Test fun twoYearLoan_Monthly_with_cashDown_and_tradeIn() {
        val vehiclePrice = 30000
        val cashDownAmount = 5000
        val tradeInAmount = 5000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        assertEquals(LoanCalculator.Result(payment = 923, costOfBorrowing = 2150),
                LoanCalculator().calculateLoan(
                        vehiclePrice = vehiclePrice,
                        termInMonths = termInMonths,
                        annualInterestRate = ANNUAL_INTEREST_RATE,
                        paymentFrequency = paymentFrequency,
                        cashDownAmount = cashDownAmount,
                        tradeInAmount = tradeInAmount))
    }

    @Test fun loan_with_the_sumOf_cashDown_and_tradeIn_equalTo_vehiclePrice() {
        val vehiclePrice = 30000
        val cashDownAmount = 15000
        val tradeInAmount = 15000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        assertEquals(LoanCalculator.Result(payment = 0, costOfBorrowing = 0),
                LoanCalculator().calculateLoan(
                        vehiclePrice = vehiclePrice,
                        termInMonths = termInMonths,
                        annualInterestRate = ANNUAL_INTEREST_RATE,
                        paymentFrequency = paymentFrequency,
                        cashDownAmount = cashDownAmount,
                        tradeInAmount = tradeInAmount))
    }




    @Test fun zero_vehiclePrice_notAcceptable() {
        val vehiclePrice = 0
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        try {
            LoanCalculator().calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = ANNUAL_INTEREST_RATE,
                    paymentFrequency = paymentFrequency)
            fail("Zero vehicle price should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun negative_vehiclePrice_notAcceptable() {
        val vehiclePrice = -30000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        try {
            LoanCalculator().calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = ANNUAL_INTEREST_RATE,
                    paymentFrequency = paymentFrequency)
            fail("Negative vehicle price [$vehiclePrice] should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun negative_cashDown_notAcceptable() {
        val vehiclePrice = 30000
        val cashDownAmount = -5000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        try {
            LoanCalculator().calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = ANNUAL_INTEREST_RATE,
                    paymentFrequency = paymentFrequency,
                    cashDownAmount = cashDownAmount)
            fail("Negative cash down amount [$cashDownAmount] should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun negative_tradeIn_notAcceptable() {
        val vehiclePrice = 30000
        val tradeInAmount = -5000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        try {
            LoanCalculator().calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = ANNUAL_INTEREST_RATE,
                    paymentFrequency = paymentFrequency,
                    tradeInAmount = tradeInAmount)
            fail("Negative trade-in amount [$tradeInAmount] should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun sumOf_cashDown_and_tradeIn_greaterThan_vehiclePrice_notAcceptable() {
        val vehiclePrice = 30000
        val cashDownAmount = 25000
        val tradeInAmount = 10000
        val termInMonths = 2 * 12
        val paymentFrequency = PaymentFrequency.MONTHLY

        try {
            LoanCalculator().calculateLoan(
                    vehiclePrice = vehiclePrice,
                    termInMonths = termInMonths,
                    annualInterestRate = ANNUAL_INTEREST_RATE,
                    paymentFrequency = paymentFrequency,
                    cashDownAmount = cashDownAmount,
                    tradeInAmount = tradeInAmount)
            fail("The sum of cash down [$cashDownAmount] and trade-in [$tradeInAmount] amounts greater than vehicle price [$vehiclePrice] should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }
}