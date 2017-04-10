package com.ntangent.carfinancecalculator.calculator.domain

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PaymentPeriodUtilTest {

    private fun paymentsPerYear(paymentFrequency: PaymentFrequency)
            = PaymentPeriodUtil.paymentsPerYear(paymentFrequency)

    @Test fun monthsPerYearTest() {
        assertEquals(12, paymentsPerYear(PaymentFrequency.MONTHLY))
    }

    @Test fun biweeksPerYearTest() {
        assertEquals(26, paymentsPerYear(PaymentFrequency.BI_WEEKLY))
    }

    @Test fun weeksPerYearTest() {
        assertEquals(52, paymentsPerYear(PaymentFrequency.WEEKLY))
    }

    @Test fun numberOfMonthlyPayments_inOneYear() {
        assertEquals(12, PaymentPeriodUtil.totalPaymentsPerTerm(
                termInMonths = 12, paymentFrequency = PaymentFrequency.MONTHLY))
    }

    @Test fun numberOfBiweeklyPayments_inOneYear() {
        assertEquals(26, PaymentPeriodUtil.totalPaymentsPerTerm(
                termInMonths = 12, paymentFrequency = PaymentFrequency.BI_WEEKLY))
    }

    @Test fun numberOfWeeklyPayments_inOneYear() {
        assertEquals(52, PaymentPeriodUtil.totalPaymentsPerTerm(
                termInMonths = 12, paymentFrequency = PaymentFrequency.WEEKLY))
    }

    @Test fun numberOfWeeklyPayments_inThreeYears() {
        assertEquals(3 * 52, PaymentPeriodUtil.totalPaymentsPerTerm(
                termInMonths = 3 * 12, paymentFrequency = PaymentFrequency.WEEKLY))
    }

    @Test fun fifteenMonthsTerm_shouldNotBeAccepted() {
        try {
            PaymentPeriodUtil.totalPaymentsPerTerm(
                    termInMonths = 15, paymentFrequency = PaymentFrequency.WEEKLY)
            fail("Term not amounting to a full year (e.g. 15 months) should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun eightMonthsTerm_shouldNotBeAccepted() {
        try {
            PaymentPeriodUtil.totalPaymentsPerTerm(
                    termInMonths = 8, paymentFrequency = PaymentFrequency.WEEKLY)
            fail("Term not amounting to a full year (e.g. 8 months) should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun negativeMonthsTerm_shouldNotBeAccepted() {
        try {
            PaymentPeriodUtil.totalPaymentsPerTerm(
                    termInMonths = -12, paymentFrequency = PaymentFrequency.WEEKLY)
            fail("Negative term months (e.g. -12) should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }

    @Test fun zeroMonthsTerm_shouldNotBeAccepted() {
        try {
            PaymentPeriodUtil.totalPaymentsPerTerm(
                    termInMonths = 0, paymentFrequency = PaymentFrequency.WEEKLY)
            fail("Zero term months should not be accepted; IllegalArgumentException expected")
        } catch (e: IllegalArgumentException) {
            // pass: expected to throw
        }
    }
}