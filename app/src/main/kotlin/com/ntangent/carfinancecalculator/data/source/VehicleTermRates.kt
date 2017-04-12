package com.ntangent.carfinancecalculator.data.source

data class VehicleTermRate(
        val Term: Int = 0,
        val Rate: Double = 0.0
)

data class VehicleTermParams(
        val VehiclePrice: Int = 0,
        val VehicleTermRates: List<VehicleTermRate> = arrayListOf()
)
