package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatter
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
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
    @Before fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test fun testSettingView() {
        val params = FinanceParams(20000, arrayListOf(VehicleTermRate(2 * 12, 10.0)))
        presenter = CalculatorPresenter(params, mockView, LoanCalculator(), FakeStringFormatter())
        presenter.subscribe()
        verifyView().setVehiclePrice("20000")
        verifyView().setPaymentAmount("923")
        verifyView().setTerm("24")
        verifyView().setRate("10.0")
        verifyView().setMinTermMonths("12")
        verifyView().setMaxTermMonths("24")
        verifyView().setPaymentFrequency(PaymentFrequency.MONTHLY)
    }

    private fun verifyView() = Mockito.verify<CalculatorContract.View>(mockView)
}