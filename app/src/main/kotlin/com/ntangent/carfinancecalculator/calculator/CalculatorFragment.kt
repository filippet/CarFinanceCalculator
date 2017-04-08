package com.ntangent.carfinancecalculator.calculator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.R

class CalculatorFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }
}