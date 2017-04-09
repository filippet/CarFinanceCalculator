package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.data.FinanceParams
import com.ntangent.carfinancecalculator.data.VehicleTermRate
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class CalculatorPresenterTest {
    private lateinit var presenter: CalculatorPresenter

    @Mock private lateinit var mockView: CalculatorContract.View

    @Before fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test fun testSettingView() {
        val params = FinanceParams(10000, arrayListOf(VehicleTermRate(12, 10.0)))
        presenter = CalculatorPresenter(params, mockView)
        presenter.subscribe()
        verifyView().setVehiclePrice("10000")
        verifyView().setPaymentAmount("25")
        verifyView().setTerm("12")
        verifyView().setRate("10.0")
        verifyView().setMinTermMonths("1")
        verifyView().setMaxTermMonths("12")
        verifyView().setPaymentFrequency(PaymentFrequency.MONTHLY)
    }

    private fun verifyView() = Mockito.verify<CalculatorContract.View>(mockView)
}