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


class RecyclerViewFragment : Fragment(), RecyclerViewContract.View {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment RecyclerViewFragment.
         */
        fun newInstance(): RecyclerViewFragment {
            return RecyclerViewFragment()
        }
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: CalculatorsRecyclerViewAdapter
    private lateinit var presenter: RecyclerViewContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        ButterKnife.bind(this, view)

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        recyclerViewAdapter = CalculatorsRecyclerViewAdapter(arrayListOf())
        recyclerView.adapter = recyclerViewAdapter

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

    //<RecyclerViewContract.View implementation>
    //
    override fun setPresenter(presenter: RecyclerViewContract.Presenter) {
        this.presenter = presenter
    }

    override fun setFinanceParamsList(financeParamsList: List<FinanceParams>) {
        recyclerViewAdapter = CalculatorsRecyclerViewAdapter(financeParamsList)
        recyclerView.adapter = recyclerViewAdapter
    }
    //
    //</RecyclerViewContract.View implementation>
}