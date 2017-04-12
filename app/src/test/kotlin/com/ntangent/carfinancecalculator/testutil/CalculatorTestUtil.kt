package com.ntangent.carfinancecalculator.testutil

import com.ntangent.carfinancecalculator.calculator.CalculatorContract
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.FinanceTermRate
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.data.source.FinanceParamsDataSource
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

/**
 * Test utils to facilitate calculator tests
 */
object TestParamsCreator {

    val PARAMS_0_VEHICLE_PRICE = 50000
    val PARAMS_0_TERM_0 = 24
    val PARAMS_0_RATE_0 = 9.99
    val PARAMS_0_TERM_1 = 36
    val PARAMS_0_RATE_1 = 8.99
    val PARAMS_0_TERM_2 = 48
    val PARAMS_0_RATE_2 = 7.99
    val PARAMS_0_TERM_3 = 60
    val PARAMS_0_RATE_3 = 6.99
    val PARAMS_0_TERM_4 = 72
    val PARAMS_0_RATE_4 = 5.99

    val PARAMS_1_VEHICLE_PRICE = 60000
    val PARAMS_1_TERM_0 = 12
    val PARAMS_1_RATE_0 = 4.99
    val PARAMS_1_TERM_1 = 24
    val PARAMS_1_RATE_1 = 3.99
    val PARAMS_1_TERM_2 = 36
    val PARAMS_1_RATE_2 = 2.99


    val PARAMS_0 = FinanceParams(
                vehiclePrice = PARAMS_0_VEHICLE_PRICE,
                termRates = arrayListOf(
                        FinanceTermRate(PARAMS_0_TERM_0, PARAMS_0_RATE_0),
                        FinanceTermRate(PARAMS_0_TERM_1, PARAMS_0_RATE_1),
                        FinanceTermRate(PARAMS_0_TERM_2, PARAMS_0_RATE_2),
                        FinanceTermRate(PARAMS_0_TERM_3, PARAMS_0_RATE_3),
                        FinanceTermRate(PARAMS_0_TERM_4, PARAMS_0_RATE_4)
                )
        )

        val PARAMS_1 = FinanceParams(
                vehiclePrice = PARAMS_1_VEHICLE_PRICE,
                termRates = arrayListOf(
                        FinanceTermRate(PARAMS_1_TERM_0, PARAMS_1_RATE_0),
                        FinanceTermRate(PARAMS_1_TERM_1, PARAMS_1_RATE_1),
                        FinanceTermRate(PARAMS_1_TERM_2, PARAMS_1_RATE_2)
                )
        )
}

class FakeGetVehicleLoanTermsObserver : DisposableObserver<List<FinanceParams>>() {
    var params: List<FinanceParams>? = null

    override fun onNext(value: List<FinanceParams>) {
        params = value
    }

    override fun onComplete() {
        // Do nothing
    }

    override fun onError(e: Throwable?) {
        // Do nothing
    }
}

class FakeDataSource: FinanceParamsDataSource {
    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.just(arrayListOf(TestParamsCreator.PARAMS_0, TestParamsCreator.PARAMS_1))
    }
}

class FakeStringFormatter: CalculatorStringFormatter {

    override fun vehiclePrice(value: Int): String {
        return value.toString()
    }

    override fun paymentAmount(value: Int, paymentFrequency: PaymentFrequency): String {
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


fun CalculatorContract.View.forTest_returnSelectedTermIndex(index: Int) {
    org.mockito.Mockito.`when`(getSelectedTermIndex()).thenReturn(index)
}

fun CalculatorContract.View.forTest_returnPaymentFrequency(paymentFrequency: PaymentFrequency) {
    org.mockito.Mockito.`when`(getPaymentFrequency()).thenReturn(paymentFrequency)
}

fun CalculatorContract.View.forTest_returnCashDownAmount(cashDownAmount: Int) {
    org.mockito.Mockito.`when`(getCashDownAmount()).thenReturn(cashDownAmount)
}

fun CalculatorContract.View.forTest_returnTradeInAmount(tradeInAmount: Int) {
    org.mockito.Mockito.`when`(getTradeInAmount()).thenReturn(tradeInAmount)
}
