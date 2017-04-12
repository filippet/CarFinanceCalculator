package com.ntangent.carfinancecalculator.calculator.dev

import android.os.Bundle
import android.support.annotation.IdRes
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
import com.ntangent.carfinancecalculator.calculator.CalculatorContract
import com.ntangent.carfinancecalculator.calculator.TermInfo
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.widget.CurrencyEditText


class SingleCalcFragment : Fragment(), CalculatorContract.View {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment SingleCalcFragment.
         */
        fun newInstance(): SingleCalcFragment {
            return SingleCalcFragment()
        }

        private val KEY_CASH_DOWN = "KEY_CASH_DOWN"
        private val KEY_TRADE_IN  = "KEY_TRADE_IN"
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

        sbTermMonths.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter.termMonthsChanged(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        rgPaymentFrequency.setOnCheckedChangeListener {
            group, checkedId ->
            val paymentFrequency = paymentFrequencyResIdToValue(checkedId)
            presenter.paymentFrequencyChanged(paymentFrequency)
        }
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

    override fun setTermBounds(value: TermInfo) {
        setMinTermMonths(value.minTerm)
        setMaxTermMonths(value.maxTerm)
        sbTermMonths.max = value.termRange
        sbTermMonths.progress = value.selectedTerm
    }

    override fun setRate(value: String) {
        tvRate.text = value
    }

    override fun setPaymentFrequency(value: PaymentFrequency) {
        rgPaymentFrequency.check(paymentFrequencyValueToResId(value))
    }

    override fun getSelectedTermIndex(): Int {
        return sbTermMonths.progress
    }

    override fun getPaymentFrequency(): PaymentFrequency {
        return paymentFrequencyResIdToValue(rgPaymentFrequency.checkedRadioButtonId)
    }

    override fun getCashDownAmount(): Int {
        return txCashDown.getAmount()
    }

    override fun getTradeInAmount(): Int {
        return txTradeIn.getAmount()
    }
    //
    //</CalculatorContract.View implementation>

    fun setMinTermMonths(value: String) {
        tvMinTerm.text = value
    }

    fun setMaxTermMonths(value: String) {
        tvMaxTerm.text = value
    }

    private class NumbersOnlyKeyBoardTransformationMethod : PasswordTransformationMethod() {
        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return source
        }
    }

    private fun paymentFrequencyResIdToValue(@IdRes resId: Int): PaymentFrequency {
        return when (resId) {
            R.id.rb_monthly  -> PaymentFrequency.MONTHLY
            R.id.rb_biweekly -> PaymentFrequency.BI_WEEKLY
            R.id.rb_weekly   -> PaymentFrequency.WEEKLY
            else -> {
                throw IllegalStateException("Unknown payment frequency radio button id[$resId]")
            }
        }
    }

    private fun paymentFrequencyValueToResId(paymentFrequency: PaymentFrequency): Int {
        return when (paymentFrequency) {
            PaymentFrequency.MONTHLY -> R.id.rb_monthly
            PaymentFrequency.BI_WEEKLY -> R.id.rb_biweekly
            PaymentFrequency.WEEKLY -> R.id.rb_weekly
        }
    }


    // The following are needed to save and restore the states of the cash down and trade in
    // EditText controls.
    // This is required as they are implemented via the inclusion of a view layout.  The problem
    // with that is the EditText controls having the same resource ID, which prevents us from
    // relying on the standard view state saving/restoring mechanism.
    //
    // The proper way to do this is embedding the mechanism in the controls themselves
    // (via SparseArrays), but due the lack of time, this will have to do.
    //
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.saveCashDown(txCashDown.getAmount())
            outState.saveTradeIn(txTradeIn.getAmount())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            txCashDown.setAmount(savedInstanceState.cashDown())
            txTradeIn.setAmount(savedInstanceState.tradeIn())
        }
    }

    private fun Bundle.cashDown(): Int {
        return this.getInt(com.ntangent.carfinancecalculator.calculator.dev.SingleCalcFragment.KEY_CASH_DOWN)
    }

    private fun Bundle.tradeIn(): Int {
        return this.getInt(com.ntangent.carfinancecalculator.calculator.dev.SingleCalcFragment.KEY_TRADE_IN)
    }

    private fun Bundle.saveCashDown(amount: Int) {
        this.putInt(com.ntangent.carfinancecalculator.calculator.dev.SingleCalcFragment.KEY_CASH_DOWN, amount)
    }

    private fun Bundle.saveTradeIn(amount: Int) {
        this.putInt(com.ntangent.carfinancecalculator.calculator.dev.SingleCalcFragment.KEY_TRADE_IN, amount)
    }

}