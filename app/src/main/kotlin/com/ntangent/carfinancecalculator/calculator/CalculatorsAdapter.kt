package com.ntangent.carfinancecalculator.calculator

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
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
import com.ntangent.carfinancecalculator.calculator.domain.CalculatorStringFormatterImpl
import com.ntangent.carfinancecalculator.calculator.domain.LoanCalculator
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.widget.CurrencyEditText

class CalculatorItemState(
        var cashDown: Int = 0,
        var tradeIn: Int = 0,
        var termIndex: Int = 0,
        var paymentFrequency: PaymentFrequency = PaymentFrequency.MONTHLY
)

class CalculatorItemData(
        val financeParams: FinanceParams,
        var state: CalculatorItemState = CalculatorItemState()
)

class CalculatorsAdapter(
        val itemDataList: List<CalculatorItemData>

): RecyclerView.Adapter<CalculatorsAdapter.CalculatorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.calculator_card, parent, false)

        val holder = CalculatorViewHolder(inflatedView)
        val presenter = CalcItemPresenter(
                holder, LoanCalculator(), CalculatorStringFormatterImpl(parent.context)
        )
        holder.setPresenter(presenter)
        return holder
    }


    override fun getItemCount(): Int {
        return itemDataList.size
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        val itemData = itemDataList[position]
        holder.bindItemData(itemData)
    }



    class CalculatorViewHolder(v: View) : RecyclerView.ViewHolder(v), CalculatorContract.View {

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

        private var tvCashDown: TextView
        private var txCashDown: CurrencyEditText

        private var tvTradeIn: TextView
        private var txTradeIn: CurrencyEditText

        private var presenter: CalculatorContract.Presenter? = null
        private lateinit var itemData: CalculatorItemData

        init {
            ButterKnife.bind(this, v)

            tvCashDown = vCashDown.findViewById(R.id.tv_name) as TextView
            txCashDown = vCashDown.findViewById(R.id.tx_value) as CurrencyEditText
            tvCashDown.setText(R.string.cash_down)

            tvTradeIn = vTradeIn.findViewById(R.id.tv_name) as TextView
            txTradeIn = vTradeIn.findViewById(R.id.tx_value) as CurrencyEditText
            tvTradeIn.setText(R.string.trade_in)

            sbTermMonths.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    itemData.state.termIndex = progress
                    if (presenter != null) {
                        presenter!!.termMonthsChanged(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })

            rgPaymentFrequency.setOnCheckedChangeListener {
                group, checkedId ->
                val paymentFrequency = paymentFrequencyResIdToValue(checkedId)
                itemData.state.paymentFrequency = paymentFrequency
                if (presenter != null) {
                    presenter!!.paymentFrequencyChanged(paymentFrequency)
                }
            }


            txCashDown.transformationMethod = NumbersOnlyKeyBoardTransformationMethod()
            txCashDown.subscribeOnValueChangeListener(object: CurrencyEditText.OnValueChangeListener {
                override fun newValue(value: Int) {
                    itemData.state.cashDown = value
                    if (presenter != null) {
                        presenter!!.cashDownAmountChanged(value)
                    }
                }
            })

            txTradeIn.transformationMethod = NumbersOnlyKeyBoardTransformationMethod()
            txTradeIn.subscribeOnValueChangeListener(object: CurrencyEditText.OnValueChangeListener {
                override fun newValue(value: Int) {
                    itemData.state.tradeIn = value
                    if (presenter != null) {
                        presenter!!.tradeInAmountChanged(value)
                    }
                }
            })
        }


        override fun setPresenter(presenter: CalculatorContract.Presenter) {
            this.presenter = presenter
        }


        fun bindItemData(itemData: CalculatorItemData) {
            this.itemData = itemData
            txCashDown.setAmount(itemData.state.cashDown)
            txTradeIn.setAmount(itemData.state.tradeIn)
            sbTermMonths.progress = itemData.state.termIndex
            rgPaymentFrequency.check(paymentFrequencyValueToResId(itemData.state.paymentFrequency))
            if (presenter != null) {
                presenter!!.setFinanceParams(itemData.financeParams)
            }
        }

        override fun setVehiclePrice(value: String) {
            tvVehiclePrice.text = value
        }

        override  fun setPaymentAmount(value: String) {
            tvPaymentAmount.text = value
        }

        override  fun setTerm(value: String) {
            tvTerm.text = value
        }

        override fun setTermBounds(value: TermInfoViewModel) {
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
                PaymentFrequency.MONTHLY   -> R.id.rb_monthly
                PaymentFrequency.BI_WEEKLY -> R.id.rb_biweekly
                PaymentFrequency.WEEKLY    -> R.id.rb_weekly
            }
        }
    }
}

