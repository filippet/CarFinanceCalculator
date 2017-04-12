package com.ntangent.carfinancecalculator.calculator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import com.ntangent.carfinancecalculator.calculator.domain.PaymentFrequency


class CalculatorsFragment : Fragment(), CalcItemContract.View {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment CalculatorsFragment.
         */
        fun newInstance(): CalculatorsFragment {
            return CalculatorsFragment()
        }


        private val KEY_ITEM_COUNT          = "item_count_"
        private val KEY_CASH_DOWN_PREFIX    = "cash_down_"
        private val KEY_TRADE_IN_PREFIX     = "trade_in_"
        private val KEY_TERM_INDEX_PREFIX   = "term_index_"
        private val KEY_PAYMENT_FREQ_PREFIX = "payment_freq_"
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CalculatorsAdapter
    private lateinit var presenter: CalcItemContract.Presenter

    private var calculatorStates = arrayListOf<CalculatorItemState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            val itemCount = savedInstanceState.getInt(KEY_ITEM_COUNT)
            calculatorStates.clear()
            for (index in 0..itemCount-1) {
                val cashDown              = savedInstanceState.getInt("$KEY_CASH_DOWN_PREFIX$index")
                val tradeIn               = savedInstanceState.getInt("$KEY_TRADE_IN_PREFIX$index")
                val termIndex             = savedInstanceState.getInt("$KEY_TERM_INDEX_PREFIX$index")
                val paymentFrequencyIndex = savedInstanceState.getInt("$KEY_PAYMENT_FREQ_PREFIX$index")
                val paymentFrequency: PaymentFrequency = PaymentFrequency.values()[paymentFrequencyIndex]
                calculatorStates.add(CalculatorItemState(
                        cashDown = cashDown,
                        tradeIn = tradeIn,
                        termIndex = termIndex,
                        paymentFrequency = paymentFrequency))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        ButterKnife.bind(this, view)

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter = CalculatorsAdapter(arrayListOf())
        recyclerView.adapter = adapter

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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) {
            val itemDataList = (recyclerView.adapter as CalculatorsAdapter).itemDataList

            outState.putInt(KEY_ITEM_COUNT, itemDataList.size)
            for ((index, itemData) in itemDataList.withIndex()) {
                outState.putInt("$KEY_CASH_DOWN_PREFIX$index"   , itemData.state.cashDown)
                outState.putInt("$KEY_TRADE_IN_PREFIX$index"    , itemData.state.tradeIn)
                outState.putInt("$KEY_TERM_INDEX_PREFIX$index"  , itemData.state.termIndex)
                outState.putInt("$KEY_PAYMENT_FREQ_PREFIX$index", itemData.state.paymentFrequency.ordinal)
            }
        }
    }
    //<CalcItemContract.View implementation>
    //
    override fun setPresenter(presenter: CalcItemContract.Presenter) {
        this.presenter = presenter
    }

    override fun setFinanceParamsList(financeParamsList: List<FinanceParams>) {
        adapter = CalculatorsAdapter(mapToCalculatorItemDataList(financeParamsList))
        recyclerView.adapter = adapter
    }
    //
    //</CalcItemContract.View implementation>

    private fun mapToCalculatorItemDataList(financeParamsList: List<FinanceParams>): List<CalculatorItemData> {
        val dataList = arrayListOf<CalculatorItemData>()

        if (calculatorStates.size == financeParamsList.size) {
            for ((index, params) in financeParamsList.withIndex()) {
                dataList.add(CalculatorItemData(params, calculatorStates[index]))
            }
        } else {
            for (params in financeParamsList) {
                dataList.add(CalculatorItemData(params))
            }
        }
        return dataList
    }

}