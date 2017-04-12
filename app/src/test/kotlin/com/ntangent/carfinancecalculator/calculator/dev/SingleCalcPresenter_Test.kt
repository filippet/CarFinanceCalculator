package com.ntangent.carfinancecalculator.calculator.dev

import com.ntangent.carfinancecalculator.calculator.CalculatorContract
import com.ntangent.carfinancecalculator.calculator.TermInfo
import com.ntangent.carfinancecalculator.calculator.dev.SingleCalcPresenter
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


class SingleCalcPresenter_Test {
    private lateinit var presenter: SingleCalcPresenter

    @Mock private lateinit var mockView: CalculatorContract.View

    class FakeStringFormatter: CalculatorStringFormatter {

        override fun vehiclePrice(value: Int): String {
            return value.toString()
        }

        override fun paymentAmount(value: Int, paymentFrequency: PaymentFrequency): String {
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
        presenter = SingleCalcPresenter(useCase, mockView, LoanCalculator(), FakeStringFormatter())
        presenter.subscribe()
    }

    @Test fun testSettingView() {
        verifyView().setVehiclePrice(PARAMS_0_VEHICLE_PRICE.toString())
        verifyView().setPaymentAmount("2307")
        verifyView().setTerm(PARAMS_0_TERM_0.toString())
        verifyView().setRate(PARAMS_0_RATE_0.toString())
        val termInfo = TermInfo(PARAMS_0_TERM_0.toString(), PARAMS_0_TERM_4.toString(), PARAMS_0_TERM_4 - PARAMS_0_TERM_0, 0)
        verifyView().setTermBounds(termInfo)
        verifyView().setPaymentFrequency(PaymentFrequency.MONTHLY)
    }

    private fun verifyView() = Mockito.verify<CalculatorContract.View>(mockView)
}