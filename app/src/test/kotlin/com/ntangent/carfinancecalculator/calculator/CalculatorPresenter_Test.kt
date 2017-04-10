package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.calculator.domain.interactor.GetVehicleLoanTermsUseCase
import com.ntangent.carfinancecalculator.data.source.FinanceParamsRepository
import com.ntangent.carfinancecalculator.testutil.FakeDataSource
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_RATE_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_4
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_VEHICLE_PRICE
import com.ntangent.carfinancecalculator.testutil.TestPostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.testutil.TestSubscriptionSchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class CalculatorPresenter_Test {
    private lateinit var presenter: CalculatorPresenter

    @Mock private lateinit var mockView: CalculatorContract.View

    class FakeStringFormatter: CalculatorStringFormatter {

        override fun vehiclePrice(value: Int): String {
            return value.toString()
        }

        override fun paymentAmount(value: Int): String {
            return value.toString()
        }

        override fun term(value: Int): String {
            return value.toString()
        }

        override fun rate(value: Double): String {
            return value.toString()
        }

        override fun minTermMonths(value: Int): String {
            return value.toString()
        }

        override fun maxTermMonths(value: Int): String {
            return value.toString()
        }
    }


    private lateinit var useCase: GetVehicleLoanTermsUseCase

    @Before fun initMocks() {
        MockitoAnnotations.initMocks(this)
        val repo = FinanceParamsRepository(FakeDataSource())
        useCase = GetVehicleLoanTermsUseCase(repo, TestSubscriptionSchedulerProvider(), TestPostExecutionSchedulerProvider())
        presenter = CalculatorPresenter(useCase, mockView, LoanCalculator(), FakeStringFormatter())
        presenter.subscribe()
    }

    @Test fun testSettingView() {
        verifyView().setVehiclePrice(PARAMS_0_VEHICLE_PRICE.toString())
        verifyView().setPaymentAmount("2307")
        verifyView().setTerm(PARAMS_0_TERM_0.toString())
        verifyView().setRate(PARAMS_0_RATE_0.toString())
        verifyView().setMinTermMonths(PARAMS_0_TERM_0.toString())
        verifyView().setMaxTermMonths(PARAMS_0_TERM_4.toString())
        verifyView().setPaymentFrequency(PaymentFrequency.MONTHLY)
    }

    private fun verifyView() = Mockito.verify<CalculatorContract.View>(mockView)
}