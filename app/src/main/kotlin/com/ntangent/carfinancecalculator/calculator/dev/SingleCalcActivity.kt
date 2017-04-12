package com.ntangent.carfinancecalculator.calculator.dev

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.ntangent.carfinancecalculator.ApplicationModule
import com.ntangent.carfinancecalculator.CalculatorApplication
import com.ntangent.carfinancecalculator.R
import com.ntangent.carfinancecalculator.util.EspressoIdlingResource
import com.ntangent.carfinancecalculator.util.addFragment
import javax.inject.Inject

class SingleCalcActivity : AppCompatActivity() {

    private val FRAGMENT_CONTAINER_VIEW_RESOURCE_ID = R.id.v_content

    @Inject lateinit var singleCalcPresenter: SingleCalcPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        ButterKnife.bind(this)

        val fragment = initializeFragment()
        initiateInjector(fragment)
    }

    private fun initiateInjector(singleCalcFragment: SingleCalcFragment) {
        DaggerSingleCalcComponent.builder()
                .singleCalcPresenterModule(
                        SingleCalcPresenterModule(singleCalcFragment))
                .applicationModule(
                        ApplicationModule(applicationContext))
                .financeParamsRepositoryComponent(
                        (application as CalculatorApplication).getFinanceParamsRepositoryComponent())
                .build()
                .inject(this)
    }

    private fun initializeFragment() : SingleCalcFragment {
        val fragment = findFragment()
        if (fragment != null) return fragment

        val newFragment = SingleCalcFragment.newInstance()
        supportFragmentManager.addFragment(FRAGMENT_CONTAINER_VIEW_RESOURCE_ID, newFragment)
        return newFragment
    }

    private fun findFragment(): SingleCalcFragment? {
        return supportFragmentManager.findFragmentById(
                FRAGMENT_CONTAINER_VIEW_RESOURCE_ID) as SingleCalcFragment?
    }

    @VisibleForTesting
    fun getCountingIdlingResource() = EspressoIdlingResource.idlingResource
}
