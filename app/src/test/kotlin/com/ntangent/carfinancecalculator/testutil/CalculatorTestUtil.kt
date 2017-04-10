package com.ntangent.carfinancecalculator.testutil

import com.ntangent.carfinancecalculator.data.FinanceParams
import com.ntangent.carfinancecalculator.data.VehicleTermRate
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
                        VehicleTermRate(PARAMS_0_TERM_0, PARAMS_0_RATE_0),
                        VehicleTermRate(PARAMS_0_TERM_1, PARAMS_0_RATE_1),
                        VehicleTermRate(PARAMS_0_TERM_2, PARAMS_0_RATE_2),
                        VehicleTermRate(PARAMS_0_TERM_3, PARAMS_0_RATE_3),
                        VehicleTermRate(PARAMS_0_TERM_4, PARAMS_0_RATE_4)
                )
        )

        val PARAMS_1 = FinanceParams(
                vehiclePrice = PARAMS_1_VEHICLE_PRICE,
                termRates = arrayListOf(
                        VehicleTermRate(PARAMS_1_TERM_0, PARAMS_1_RATE_0),
                        VehicleTermRate(PARAMS_1_TERM_1, PARAMS_1_RATE_1),
                        VehicleTermRate(PARAMS_1_TERM_2, PARAMS_1_RATE_2)
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
