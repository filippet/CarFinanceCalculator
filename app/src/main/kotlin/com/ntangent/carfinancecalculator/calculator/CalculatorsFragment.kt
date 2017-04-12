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
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CalculatorsAdapter
    private lateinit var presenter: CalcItemContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    //<CalcItemContract.View implementation>
    //
    override fun setPresenter(presenter: CalcItemContract.Presenter) {
        this.presenter = presenter
    }

    override fun setFinanceParamsList(financeParamsList: List<FinanceParams>) {
        adapter = CalculatorsAdapter(financeParamsList)
        recyclerView.adapter = adapter
    }
    //
    //</CalcItemContract.View implementation>
}