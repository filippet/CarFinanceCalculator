package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.data.FinanceParams
import com.ntangent.carfinancecalculator.data.VehicleTermRate
import io.reactivex.Observable

/**
 * Fake local data source.
 */
class FakeLocalFinanceParamsDataSource: FinanceParamsDataSource {
    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.just(makeParams())
    }

    private fun makeParams(): List<FinanceParams> {
        return arrayListOf(
                FinanceParams(
                        vehiclePrice = 48801,
                        termRates = arrayListOf(
                                VehicleTermRate(24, 9.99),
                                VehicleTermRate(36, 8.99),
                                VehicleTermRate(48, 7.99),
                                VehicleTermRate(60, 6.99),
                                VehicleTermRate(72, 5.99)
                        )
                ),

                FinanceParams(
                        vehiclePrice = 5600,
                        termRates = arrayListOf(
                                VehicleTermRate(12, 4.99),
                                VehicleTermRate(24, 3.99),
                                VehicleTermRate(36, 2.99)
                        )
                )
        )
    }
}