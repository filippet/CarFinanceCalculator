package com.ntangent.carfinancecalculator.data

data class VehicleTermRate(
        val term: Int,
        val rate: Double
)

data class FinanceParams(
        val vehiclePrice: Int,
        val termRates: List<VehicleTermRate>
)