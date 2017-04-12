package com.ntangent.carfinancecalculator.data.source

/**
 * Created by filip on 4/12/17.
 */
interface VehicleTermParamsJsonDeserializer {
    fun deserialize(jsonString: String): List<VehicleTermParams>
}