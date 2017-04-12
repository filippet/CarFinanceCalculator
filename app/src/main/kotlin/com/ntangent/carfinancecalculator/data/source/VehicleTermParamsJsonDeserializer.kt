package com.ntangent.carfinancecalculator.data.source

interface VehicleTermParamsJsonDeserializer {
    fun deserialize(jsonString: String): List<VehicleTermParams>
}