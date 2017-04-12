package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.testutil.*
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_RATE_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_0
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_TERM_4
import com.ntangent.carfinancecalculator.testutil.TestParamsCreator.PARAMS_0_VEHICLE_PRICE
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CalcItemPresenter_Test {
    private lateinit var presenter: CalcItemPresenter

    @Mock private lateinit var mockView: CalculatorContract.View

    @Before fun initMocks() {
        MockitoAnnotations.initMocks(this)

        mockView.forTest_returnSelectedTermIndex(0)
        mockView.forTest_returnPaymentFrequency(PaymentFrequency.MONTHLY)
        mockView.forTest_returnCashDownAmount(0)
        mockView.forTest_returnTradeInAmount(0)

        presenter = CalcItemPresenter(mockView, LoanCalculator(), FakeStringFormatter())
        presenter.setFinanceParams(PARAMS_0)
    }

    @Test fun testSettingView() {
        verifyView().setVehiclePrice(PARAMS_0_VEHICLE_PRICE.toString())
        verifyView().setPaymentAmount("2307")
        verifyView().setTerm(PARAMS_0_TERM_0.toString())
        verifyView().setRate(PARAMS_0_RATE_0.toString())
        val termInfo = TermInfoViewModel(PARAMS_0_TERM_0.toString(), PARAMS_0_TERM_4.toString(), (PARAMS_0_TERM_4 - PARAMS_0_TERM_0) / 12, 0)
        verifyView().setTermBounds(termInfo)
        verifyView().setPaymentFrequency(PaymentFrequency.MONTHLY)
    }

    private fun verifyView() = Mockito.verify<CalculatorContract.View>(mockView)
}