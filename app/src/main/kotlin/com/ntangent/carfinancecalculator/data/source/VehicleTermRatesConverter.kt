package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.FinanceTermRate

/**
 * Created by filip on 4/11/17.
 */
class VehicleTermRatesConverter {

    fun convertToFinanceParams(vehicleTermParams: VehicleTermParams): FinanceParams {
        val financeTermRates = arrayListOf<FinanceTermRate>()
        for (param in vehicleTermParams.VehicleTermRates) {
            financeTermRates.add(FinanceTermRate(param.Term, param.Rate))
        }
        return FinanceParams(vehicleTermParams.VehiclePrice, financeTermRates)
    }

    fun convertToFinanceParamsList(vehicleTermParamsList: List<VehicleTermParams>): List<FinanceParams> {
        val financeParamsList = arrayListOf<FinanceParams>()
        for (params in vehicleTermParamsList) {
            financeParamsList.add(convertToFinanceParams(params))
        }
        return financeParamsList
    }
}