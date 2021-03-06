package com.ntangent.carfinancecalculator.calculator

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

class CalculatorsActivity : AppCompatActivity() {

    private val FRAGMENT_CONTAINER_VIEW_RESOURCE_ID = R.id.v_content

    @Inject lateinit var fragmentPresenter: CalculatorsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_activity)
        ButterKnife.bind(this)

        val fragment = initializeFragment()
        initiateInjector(fragment)
    }

    private fun initiateInjector(calculatorsFragment: CalculatorsFragment) {
        DaggerCalculatorsComponent.builder()
                .calculatorsPresenterModule(
                        CalculatorsPresenterModule(calculatorsFragment))
                .applicationModule(
                        ApplicationModule(applicationContext))
                .financeParamsRepositoryComponent(
                        (application as CalculatorApplication).getFinanceParamsRepositoryComponent())
                .build()
                .inject(this)
    }

    private fun initializeFragment() : CalculatorsFragment {
        val fragment = findFragment()
        if (fragment != null) return fragment

        val newFragment = CalculatorsFragment.newInstance()
        supportFragmentManager.addFragment(FRAGMENT_CONTAINER_VIEW_RESOURCE_ID, newFragment)
        return newFragment
    }

    private fun findFragment(): CalculatorsFragment? {
        return supportFragmentManager.findFragmentById(
                FRAGMENT_CONTAINER_VIEW_RESOURCE_ID) as CalculatorsFragment?
    }

    @VisibleForTesting
    fun getCountingIdlingResource() = EspressoIdlingResource.idlingResource
}
