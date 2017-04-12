package com.ntangent.carfinancecalculator.data.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import kotlin.test.assertEquals

class VehicleTermParams_JsonDeserialization_Test {

    @Test fun deserializeVehicleTermRate() {
        val jsonStr = """ { "Term": 24, "Rate": 6.99 } """
        val jsonObj = Gson().fromJson(jsonStr, VehicleTermRate::class.java)
        assertEquals(VehicleTermRate(24, 6.99), jsonObj)
    }

    @Test fun deserializeSingleVehicleTermParams() {
        val jsonStr = """
              {
                "VehiclePrice": 5600,
                "VehicleTermRates": [
                    { "Term": 12, "Rate": 6.99 },
                    { "Term": 24, "Rate": 5.99 },
                    { "Term": 36, "Rate": 7.99 } ]
              }
        """
        val expected = VehicleTermParams(5600, arrayListOf(
                VehicleTermRate(12, 6.99),
                VehicleTermRate(24, 5.99),
                VehicleTermRate(36, 7.99)))
        val jsonObj = Gson().fromJson(jsonStr, VehicleTermParams::class.java)
        assertEquals(expected, jsonObj)
    }

    @Test fun deserializeTwoVehicleTermParams() {
        val jsonStr = """
            [
              {
                    "VehiclePrice": 1000,
                    "VehicleTermRates": [
                        { "Term": 12, "Rate": 1.99 },
                        { "Term": 24, "Rate": 2.99 },
                        { "Term": 36, "Rate": 3.99 }
                    ]
              },
              {     "VehiclePrice": 2000,
                    "VehicleTermRates": [
                        { "Term": 48, "Rate": 4.99 },
                        { "Term": 60, "Rate": 5.99 },
                        { "Term": 72, "Rate": 6.99 }
                    ]
              }
            ]
        """
        val expected = arrayListOf<VehicleTermParams>(
                VehicleTermParams(1000, arrayListOf(
                        VehicleTermRate(12, 1.99),
                        VehicleTermRate(24, 2.99),
                        VehicleTermRate(36, 3.99))),
                VehicleTermParams(2000, arrayListOf(
                        VehicleTermRate(48, 4.99),
                        VehicleTermRate(60, 5.99),
                        VehicleTermRate(72, 6.99)))
        )

        val jsonObj = Gson().fromJson<List<VehicleTermParams>>(
                jsonStr, object : TypeToken<List<VehicleTermParams>>(){}.type)
        assertEquals(expected, jsonObj)
    }
}