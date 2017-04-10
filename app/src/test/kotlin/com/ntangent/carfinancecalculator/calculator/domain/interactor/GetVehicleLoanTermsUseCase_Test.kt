package com.ntangent.carfinancecalculator.calculator.domain.interactor

import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepository
import com.ntangent.carfinancecalculator.testutil.FakeDataSource
import com.ntangent.carfinancecalculator.testutil.FakeGetVehicleLoanTermsObserver
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_1
import com.ntangent.carfinancecalculator.testutil.TestPostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.testutil.TestSubscriptionSchedulerProvider
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetVehicleLoanTermsUseCase_Test {

    private lateinit var useCase: GetVehicleLoanTermsUseCase
    private lateinit var testObserver: FakeGetVehicleLoanTermsObserver

    @Before fun setUp() {
        testObserver = FakeGetVehicleLoanTermsObserver()
        val repo = FinanceParamsRepository(FakeDataSource())
        useCase = GetVehicleLoanTermsUseCase(repo, TestSubscriptionSchedulerProvider(), TestPostExecutionSchedulerProvider())
        useCase.execute(GetVehicleLoanTermsUseCase.params(), testObserver)
    }

    @Test fun verifyParams() {
        assertEquals(2, testObserver.params!!.size)
        assertEquals(PARAMS_1, testObserver.params!![1])
    }
}