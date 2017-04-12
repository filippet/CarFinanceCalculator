package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.FinanceTermRate
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_RATE_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_RATE_1
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_1
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_VEHICLE_PRICE
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1_RATE_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1_RATE_1
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1_TERM_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1_TERM_1
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1_VEHICLE_PRICE
import org.junit.Test
import kotlin.test.assertEquals

class VehicleTermRatesConverter_Test {

    companion object {
        val FINANCE_PARAMS_0 = FinanceParams(PARAMS_0_VEHICLE_PRICE, arrayListOf(
                FinanceTermRate(PARAMS_0_TERM_0, PARAMS_0_RATE_0),
                FinanceTermRate(PARAMS_0_TERM_1, PARAMS_0_RATE_1)
        ))

        val FINANCE_PARAMS_1 = FinanceParams(PARAMS_1_VEHICLE_PRICE, arrayListOf(
                FinanceTermRate(PARAMS_1_TERM_0, PARAMS_1_RATE_0),
                FinanceTermRate(PARAMS_1_TERM_1, PARAMS_1_RATE_1)
        ))


        val VEHICLE_TERM_PARAMS_0 = VehicleTermParams(PARAMS_0_VEHICLE_PRICE, arrayListOf(
                VehicleTermRate(PARAMS_0_TERM_0, PARAMS_0_RATE_0),
                VehicleTermRate(PARAMS_0_TERM_1, PARAMS_0_RATE_1)
        ))

        val VEHICLE_TERM_PARAMS_1 = VehicleTermParams(PARAMS_1_VEHICLE_PRICE, arrayListOf(
                VehicleTermRate(PARAMS_1_TERM_0, PARAMS_1_RATE_0),
                VehicleTermRate(PARAMS_1_TERM_1, PARAMS_1_RATE_1)
        ))
    }

    @Test fun convertSingleObject() {
        assertEquals(FINANCE_PARAMS_0,
                VehicleTermRatesConverter().convertToFinanceParams(VEHICLE_TERM_PARAMS_0))
    }

    @Test fun convertTwoObjects() {
        val expectedFinanceParamList = arrayListOf<FinanceParams>(
                FINANCE_PARAMS_0, FINANCE_PARAMS_1)
        val vehicleTermParamList = arrayListOf<VehicleTermParams>(
                VEHICLE_TERM_PARAMS_0, VEHICLE_TERM_PARAMS_1)

        assertEquals(expectedFinanceParamList,
                VehicleTermRatesConverter().convertToFinanceParamsList(vehicleTermParamList))
    }

}