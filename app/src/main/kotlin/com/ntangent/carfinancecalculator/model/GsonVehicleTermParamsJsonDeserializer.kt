package com.ntangent.carfinancecalculator.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ntangent.carfinancecalculator.data.source.VehicleTermParams
import com.ntangent.carfinancecalculator.data.source.VehicleTermParamsJsonDeserializer

class GsonVehicleTermParamsJsonDeserializer: VehicleTermParamsJsonDeserializer {
    override fun deserialize(jsonString: String): List<VehicleTermParams> {
        return Gson().fromJson<List<VehicleTermParams>>(
                jsonString, object : TypeToken<List<VehicleTermParams>>(){}.type)
    }
}