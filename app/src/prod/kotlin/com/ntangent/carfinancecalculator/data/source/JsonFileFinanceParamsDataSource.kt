package com.ntangent.carfinancecalculator.data.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import io.reactivex.Observable


/**
 * Real (production) local data source.
 */
class JsonFileFinanceParamsDataSource(
        val jsonFileReader: JsonFileReader) : FinanceParamsDataSource {

    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.just(readFromJsonFile())
    }

    private fun readFromJsonFile(): List<FinanceParams> {
        return VehicleTermRatesConverter().convertToFinanceParamsList(parseParams())
    }

    private fun parseParams(): List<VehicleTermParams> {
        val jsonString = jsonFileReader.readJsonFile()
        return Gson().fromJson<List<VehicleTermParams>>(
                jsonString, object : TypeToken<List<VehicleTermParams>>(){}.type)
    }
}