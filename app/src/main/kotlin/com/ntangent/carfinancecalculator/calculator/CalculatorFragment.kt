package com.ntangent.carfinancecalculator.calculator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.widget.CurrencyEditText


class CalculatorFragment : Fragment(), CalculatorContract.View {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment CalculatorFragment.
         */
        fun newInstance(): CalculatorFragment {
            return CalculatorFragment()
        }
    }

    @BindView(R.id.tv_vehicle_price    ) lateinit var tvVehiclePrice     : TextView
    @BindView(R.id.tv_payment_amount   ) lateinit var tvPaymentAmount    : TextView
    @BindView(R.id.tv_term             ) lateinit var tvTerm             : TextView
    @BindView(R.id.tv_rate             ) lateinit var tvRate             : TextView
    @BindView(R.id.tv_min_term         ) lateinit var tvMinTerm          : TextView
    @BindView(R.id.tv_max_term         ) lateinit var tvMaxTerm          : TextView
    @BindView(R.id.sb_term_months      ) lateinit var sbTermMonths       : SeekBar
    @BindView(R.id.rg_payment_frequency) lateinit var rgPaymentFrequency : RadioGroup
    @BindView(R.id.v_cash_down         ) lateinit var vCashDown          : View
    @BindView(R.id.v_trade_in          ) lateinit var vTradeIn           : View

    private lateinit var tvCashDown: TextView
    private lateinit var txCashDown: CurrencyEditText

    private lateinit var tvTradeIn: TextView
    private lateinit var txTradeIn: CurrencyEditText

    private lateinit var presenter: CalculatorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_fragment, container, false)
        ButterKnife.bind(this, view)

        tvCashDown = vCashDown.findViewById(R.id.tv_name) as TextView
        txCashDown = vCashDown.findViewById(R.id.tx_value) as CurrencyEditText
        tvCashDown.setText(R.string.cash_down)

        tvTradeIn = vTradeIn.findViewById(R.id.tv_name) as TextView
        txTradeIn = vTradeIn.findViewById(R.id.tx_value) as CurrencyEditText
        tvTradeIn.setText(R.string.trade_in)

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()

        txCashDown.transformationMethod = NumbersOnlyKeyBoardTransformationMethod()
        txCashDown.subscribeOnValueChangeListener(object: CurrencyEditText.OnValueChangeListener {
            override fun newValue(value: Int) {
                presenter.cashDownAmountChanged(value)
            }
        })

        txTradeIn.transformationMethod = NumbersOnlyKeyBoardTransformationMethod()
        txTradeIn.subscribeOnValueChangeListener(object: CurrencyEditText.OnValueChangeListener {
            override fun newValue(value: Int) {
                presenter.tradeInAmountChanged(value)
            }
        })
    }


    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
        txCashDown.removeOnValueChangeListener()
        txTradeIn.removeOnValueChangeListener()
    }

    override fun setPresenter(presenter: CalculatorContract.Presenter) {
        this.presenter = presenter
    }

    //<CalculatorContract.View implementation>
    //
    override fun setVehiclePrice(value: String) {
        tvVehiclePrice.text = value
    }

    override fun setPaymentAmount(value: String) {
        tvPaymentAmount.text = value
    }

    override fun setTerm(value: String) {
        tvTerm.text = value
    }

    override fun setRate(value: String) {
        tvRate.text = value
    }

    override fun setMinTermMonths(value: String) {
        tvMinTerm.text = value
    }

    override fun setMaxTermMonths(value: String) {
        tvMaxTerm.text = value
    }

    override fun setPaymentFrequency(value: PaymentFrequency) {
        when (value) {
            PaymentFrequency.MONTHLY   -> rgPaymentFrequency.check(R.id.rb_monthly)
            PaymentFrequency.BI_WEEKLY -> rgPaymentFrequency.check(R.id.rb_biweekly)
            PaymentFrequency.WEEKLY    -> rgPaymentFrequency.check(R.id.rb_weekly)
        }
    }

    override fun getCashDownAmount(): Int {
        return txCashDown.getAmount()
    }

    override fun getTradeInAmount(): Int {
        return txTradeIn.getAmount()
    }
    //
    //</CalculatorContract.View implementation>

    private class NumbersOnlyKeyBoardTransformationMethod : PasswordTransformationMethod() {
        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return source
        }
    }
}