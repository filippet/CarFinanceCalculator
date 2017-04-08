package com.ntangent.carfinancecalculator.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.util.addFragment

class CalculatorActivity : AppCompatActivity() {

    private val FRAGMENT_CONTAINER_VIEW_RESOURCE_ID = R.id.v_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        ButterKnife.bind(this)

        initializeFragment()
    }

    private fun initializeFragment() : CalculatorFragment {
        val fragment = findFragment()
        if (fragment != null) return fragment

        val newFragment = CalculatorFragment.newInstance()
        supportFragmentManager.addFragment(FRAGMENT_CONTAINER_VIEW_RESOURCE_ID, newFragment)
        return newFragment
    }

    private fun findFragment(): CalculatorFragment? {
        return supportFragmentManager.findFragmentById(
                FRAGMENT_CONTAINER_VIEW_RESOURCE_ID) as CalculatorFragment?
    }
}
