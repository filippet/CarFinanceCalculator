package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import io.reactivex.Observable


/**
 * Real (production) local data source.
 */
class JsonFileFinanceParamsDataSource(
        val jsonFileReader: JsonFileReader,
        val jsonDeserializer: VehicleTermParamsJsonDeserializer) : FinanceParamsDataSource {

    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.just(readFromJsonFile())
    }

    private fun readFromJsonFile(): List<FinanceParams> {
        return VehicleTermRatesConverter().convertToFinanceParamsList(parseParams())
    }

    private fun parseParams(): List<VehicleTermParams> {
        val jsonString = jsonFileReader.readJsonFile()
        return jsonDeserializer.deserialize(jsonString)
    }
}