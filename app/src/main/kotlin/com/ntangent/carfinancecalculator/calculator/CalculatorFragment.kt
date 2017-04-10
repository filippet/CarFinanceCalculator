package com.ntangent.carfinancecalculator.calculator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency

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
    @BindView(R.id.tx_cash_down        ) lateinit var txCashDown         : EditText
    @BindView(R.id.tx_trade_in         ) lateinit var txTradeIn          : EditText

   private lateinit var presenter: CalculatorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
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
    //
    //</CalculatorContract.View implementation>
}