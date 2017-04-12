package com.ntangent.carfinancecalculator.calculator.domain

/**
 * This class performs calculations for determining (equal) payments per selected payment frequency
 * and total cost of borrowing when taking a fixed payment loan (in our case for car purchasing.)
 *
 *
 * The following is the explanation of the applied calculations.
 *
 * The loan is described with the following parameters:
 *    L - the loan, borrowed amount or principal (e.g. $20,000)
 *    Ia - annual interest rate, expressed as percentage (e.g. 10%)
 *    Tm - loan's term, expressed as number of months
 *    F - selected payment frequency (monthly, bi-weekly, or weekly)
 *
 * We want to calculate the following:
 *    Pp - periodic payment, the fixed amount needs to be paid at the end of each payment period (month, two weeks, or week)
 *    Cb - total cost of borrowing, which is the amount paid for the interest over the loan term period.
 *
 * Let's first calculate the interest rate per each payment period, Ip, expressed as decimal.
 * The interest rate is given as the annual interest rate Ia, therefore Ip is annual rate
 * divided by the number of payment periods per year:
 *
 *      Ip = Ia / Na / 100
 *
 * where:
 *      Ip - the interest rate per each payment period.  It is expressed as decimal, hence division by 100 (as Ip is given as a percentage)
 *      Na - the number of payment periods per year (12 for monthly, 26 for bi-weekly, and 52 for weekly payment frequency)
 *
 * In our case, Ip = 10.0 / 12 / 100 = 0.008333333 (for selected monthly payment frequency)
 *
 * Let's walk through the payment periods.
 *      L0 = L   -- the owned amount (L0) at the moment of borrowing is equal to the borrowed amount
 *      L1 = (1 + Ip)L0 = (1 + Ip)L - Pp   -- amount owned at the end of the first payment period
 *      L2 = (1 + Ip)L1 = (1 + Ip)((1 + Ip)L - Pp) - Pp = L(1 + Pp)^2 - Pp(1 + (1 + Ip))  -- amount owned at the end of the second payment period
 *      L3 = (1 + Ip)L2 = L(1 + Pp)^3 - Pp(1 + (1 + Ip) + (1 + Ip)^2)  -- amount owned at the end of the third payment period
 *      ...
 *
 * At the end of the last (n-th) payment period (at the loan maturity), the amount owned is:
 * (1)  Ln = L(1 + Ip)^n - Pp(1 + (1 + Ip) + (1 + Ip)^2+ ... + (1 + Ip)^(n - 1))
 *
 * (n is the total number of payment periods during the loan term.  It is calculated as:
 *     n = Ta * Na
 *  where Ta is loan's term, expressed as number of years (function of Tm), and Na is described above.
 * )
 *
 * If we label q = 1 + Ip, the portion of the formula multiplying Pp becomes:
 *      1 + q + q^2 + q^3 + ... + q^(n - 1)
 * forming a geometric progression.  We know that the sum of first n elements of geometric progression is:
 *      S(n) = (q^(n + 1) - 1)/ (q - 1)
 * And since we have n-1 elements it becomes:
 *      S(n-1) = (q^n - 1) / (q - 1)
 * Replacing q with 1 + Ip:
 * (2)  S(n-1) = ((1 + Ip)^n - 1) / Ip
 *
 * Substituting (2) into (1):
 * (3)  Ln = L(1 + Ip)^n - Pp(((1 + Ip)^n - 1) / Ip)
 *
 * Knowing that at the end of last payment period, the loan is fully paid and amount owned is 0:
 *      Ln = 0
 * then (3) becomes:
 *      0 = L(1 + Ip)^n - Pp(((1 + Ip)^n - 1) / Ip)
 * From here, Pp is calculated as:
 *      Pp = L(Ip(1 + Ip)^n) / ((1 + Ip)^n - 1))
 *
 * And by further reduction of the numerator and denominator by (1 + Ip)^n, we get the final formula
 * for calculating the fix payment amount per payment period:
 *
 *     +-----------------------------------------+
 * (4) |    Pp = L * Ip / (1 - (1 + Ip)^(-n))    |
 *     +-----------------------------------------+
 *
 * The total cost of borrowing is now calculated as the difference of total amount paid and borrowed amount:
 *
 *     +-----------------------+
 * (5) |    Cb = n * Pp - L    |
 *     +-----------------------+
 */
class LoanCalculator {

    data class Result(
            val payment: Int,
            val costOfBorrowing: Int
    )

    /**
     * Calculates fixed payment amount per payment period and total cost of borrowing when taking a
     * loan for purchasing a vehicle.
     *
     * The loan amount is calculated by subtracting down payment from the vehicle price.
     * The down payment is comprised by cash down and trade-in amounts (both optional.)
     *
     * @param vehiclePrice - the price of the vehicle to be purchased
     * @param termInMonths - the loan's term, expressed as number of months; must be multiple of 12
     * @param annualInterestRate - annual interest rate, expressed as a percentage
     * @param paymentFrequency - payment frequency (monthly, bi-weekly, or weekly)
     * @param cashDownAmount - the cash portion of the down payment
     * @param tradeInAmount - the value of user's current vehicle that they want to trade-in; is considered as a part of down payment
     *
     * @return Result object with following attributes:
     *  payment - fixed payment amount per payment period, and
     *  costOfBorrowing - total cost of borrowing
     */
    fun calculateLoan(
            vehiclePrice: Int,
            termInMonths: Int,
            annualInterestRate: Double,
            paymentFrequency: PaymentFrequency,
            cashDownAmount: Int = 0,
            tradeInAmount: Int = 0): Result {

        verifyLoanAmounts(
                cashDownAmount = cashDownAmount,
                tradeInAmount = tradeInAmount,
                vehiclePrice = vehiclePrice)

        val loanAmount = vehiclePrice - cashDownAmount - tradeInAmount
        val totalPayments = PaymentPeriodUtil.totalPaymentsPerTerm(termInMonths, paymentFrequency)
        val payment = calculatePayment(loanAmount, totalPayments, annualInterestRate, paymentFrequency)
        val costOfBorrowing = calculateCostOfBorrowing(loanAmount, payment, totalPayments)
        return Result(roundToInt(payment), costOfBorrowing)
    }

    private fun calculatePayment(
            loanAmount: Int,
            totalPayments: Int,
            annualInterestRate: Double,
            paymentFrequency: PaymentFrequency): Double {

        val paymentsPerYear = paymentsPerYear(paymentFrequency)
        val interestPerPaymentPeriod = interestPerPaymentPeriod(annualInterestRate, paymentsPerYear)
        return (interestPerPaymentPeriod * loanAmount) / (1 - Math.pow(1 + interestPerPaymentPeriod, -totalPayments.toDouble()))
    }

    private fun calculateCostOfBorrowing(loanAmount: Int, payment: Double, totalPayments: Int)
            = roundToInt(payment * totalPayments - loanAmount)

    private fun paymentsPerYear(paymentFrequency: PaymentFrequency)
            = PaymentPeriodUtil.paymentsPerYear(paymentFrequency)

    private fun interestPerPaymentPeriod(annualInterestRate: Double, paymentsPerYear: Int)
            = annualInterestRate / paymentsPerYear / 100

    private fun roundToInt(value: Double) = Math.round(value).toInt()

    private fun verifyLoanAmounts(
            vehiclePrice: Int,
            cashDownAmount: Int,
            tradeInAmount: Int) {

        assertPositive("Vehicle price", vehiclePrice)
        assertZeroOrPositive("Cash down amount", cashDownAmount)
        assertZeroOrPositive("Trade-in amount", tradeInAmount)
        verifyDownpayment(
                cashDownAmount = cashDownAmount,
                tradeInAmount = tradeInAmount,
                vehiclePrice = vehiclePrice)
    }

    private fun verifyDownpayment(
            vehiclePrice: Int,
            cashDownAmount: Int,
            tradeInAmount: Int) {

        if (cashDownAmount + tradeInAmount > vehiclePrice) {
            throw NoLoanRequiredException(
                    "The sum of cash down [$cashDownAmount] and trade-in amounts [$tradeInAmount] must not be greater than the vehicle price [$vehiclePrice]"
            )
        }
    }

    private fun assertZeroOrPositive(name: String, value: Int) {
        if (value < 0) throw IllegalArgumentException("$name must not be negative")
    }

    private fun assertPositive(name: String, value: Int) {
        if (value <= 0) throw IllegalArgumentException("$name must not be negative")
    }

}


internal class PaymentPeriodUtil {
    companion object {

        private val MONTHS_PER_YEAR  = 12
        private val BIWEEKS_PER_YEAR = 26
        private val WEEKS_PER_YEAR   = 52

        fun totalPaymentsPerTerm(termInMonths: Int, paymentFrequency: PaymentFrequency): Int {
            val years = monthsToYears(termInMonths)
            return years * paymentsPerYear(paymentFrequency)
        }

        fun paymentsPerYear(paymentFrequency: PaymentFrequency)
                = when (paymentFrequency) {
                    PaymentFrequency.MONTHLY   -> MONTHS_PER_YEAR
                    PaymentFrequency.BI_WEEKLY -> BIWEEKS_PER_YEAR
                    PaymentFrequency.WEEKLY    -> WEEKS_PER_YEAR
                }

        private fun monthsToYears(months: Int): Int {
            if (months <= 0) {
                throw IllegalArgumentException("Number of term months [$months] must be greater than zero")
            }
            val exactYears = months.toDouble() / MONTHS_PER_YEAR
            val years = exactYears.toInt()
            if (exactYears - years != 0.0) {
                throw IllegalArgumentException("Number of term months [$months] doesn't amount to full year(s)")
            }
            return years
        }
    }
}