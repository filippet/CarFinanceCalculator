package com.ntangent.carfinancecalculator.data.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.FinanceTermRate
import io.reactivex.Observable

/**
 * Fake local data source.
 */
class FakeLocalFinanceParamsDataSource: FinanceParamsDataSource {
    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.just(makeParams())
//        return Observable.just(readFromFakeJson())
    }

    private fun makeParams(): List<FinanceParams> {
        return arrayListOf(
                FinanceParams(
                        vehiclePrice = 48801,
                        termRates = arrayListOf(
                                FinanceTermRate(24, 9.99),
                                FinanceTermRate(36, 8.99),
                                FinanceTermRate(48, 7.99),
                                FinanceTermRate(60, 6.99),
                                FinanceTermRate(72, 5.99)
                        )
                ),

                FinanceParams(
                        vehiclePrice = 5600,
                        termRates = arrayListOf(
                                FinanceTermRate(12, 4.99),
                                FinanceTermRate(24, 3.99),
                                FinanceTermRate(36, 2.99)
                        )
                )
        )
    }

    private fun readFromFakeJson(): List<FinanceParams> {
        return VehicleTermRatesConverter().convertToFinanceParamsList(parseParams())
    }

    private fun parseParams(): List<VehicleTermParams> {
        return Gson().fromJson<List<VehicleTermParams>>(
                jsonStr, object : TypeToken<List<VehicleTermParams>>(){}.type)
    }

    companion object {
        private val jsonStr =
"""
[
  {
    "VehiclePrice": 48801,
    "VehicleTermRates": [
      { "Term": 24, "Rate": 6.99 },
      { "Term": 36, "Rate": 5.99 },
      { "Term": 48, "Rate": 8.99 },
      { "Term": 60, "Rate": 7.99 },
      { "Term": 72, "Rate": 7.99 }
    ]
  },
  {
    "VehiclePrice": 5600,
    "VehicleTermRates": [
      { "Term": 12, "Rate": 6.99 },
      { "Term": 24, "Rate": 5.99 },
      { "Term": 36, "Rate": 7.99 }
    ]
  },
  {
    "VehiclePrice": 48760,
    "VehicleTermRates": [
      { "Term": 36, "Rate": 6.99 },
      { "Term": 48, "Rate": 5.99 },
      { "Term": 60, "Rate": 4.99 },
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  },
  {
    "VehiclePrice": 78000,
    "VehicleTermRates": [
      { "Term": 36, "Rate": 5.99 }
    ]
  },
  {
    "VehiclePrice": 1200,
    "VehicleTermRates": [
      { "Term": 36, "Rate": 6.99 },
      { "Term": 48, "Rate": 5.99 }
    ]
  },
  {
    "VehiclePrice": 45000,
    "VehicleTermRates": [
      { "Term": 36, "Rate": 6.99 },
      { "Term": 48, "Rate": 5.99 },
      { "Term": 60, "Rate": 4.99 },
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  },
  {
    "VehiclePrice": 64000,
    "VehicleTermRates": [
      { "Term": 48, "Rate": 5.99 },
      { "Term": 60, "Rate": 4.99 },
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  },
  {
    "VehiclePrice": 17000,
    "VehicleTermRates": [
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  },
  {
    "VehiclePrice": 14000,
    "VehicleTermRates": [
      { "Term": 60, "Rate": 4.99 },
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  },
  {
    "VehiclePrice": 65000,
    "VehicleTermRates": [
      { "Term": 12, "Rate": 6.99 },
      { "Term": 24, "Rate": 5.99 },
      { "Term": 36, "Rate": 6.99 },
      { "Term": 48, "Rate": 5.99 },
      { "Term": 60, "Rate": 4.99 },
      { "Term": 72, "Rate": 3.99 },
      { "Term": 84, "Rate": 3.99 }
    ]
  }
]
"""
    }
}