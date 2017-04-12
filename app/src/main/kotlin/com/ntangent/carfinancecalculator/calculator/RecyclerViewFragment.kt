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
import com.ntangent.carfinancecalculator.calculator.domain.FinanceTermRate


class RecyclerViewFragment : Fragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment CalculatorFragment.
         */
        fun newInstance(): RecyclerViewFragment {
            return RecyclerViewFragment()
        }
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: CalculatorsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        ButterKnife.bind(this, view)

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        val financeParamsList = makeParams()
        recyclerViewAdapter = CalculatorsRecyclerViewAdapter(financeParamsList)
        recyclerView.adapter = recyclerViewAdapter

        return view
    }


    private fun makeParams(): List<FinanceParams> {
        return arrayListOf(
                FinanceParams(
                        vehiclePrice = 48801,
                        termRates = arrayListOf(
                                FinanceTermRate(24, 9.99),
                                FinanceTermRate(36, 8.99),
                                FinanceTermRate(48, 7.99),
                                FinanceTermRate(60, 6.99),
                                FinanceTermRate(72, 5.99)
                        )
                ),

                FinanceParams(
                        vehiclePrice = 5600,
                        termRates = arrayListOf(
                                FinanceTermRate(12, 4.99),
                                FinanceTermRate(24, 3.99),
                                FinanceTermRate(36, 2.99)
                        )
                )
        )
    }
}