package com.ntangent.carfinancecalculator.calculator

import android.content.Intent
import android.support.annotation.IdRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.ntangent.carfinancecalculator.R
import org.junit.Rule
import org.junit.Test

class CalculatorActivityScreenTest {

    // See:
    //      http://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
    // for the lack of support in Kotlin for using @Rule here
    //@Rule - cannot be used here (see link above); use @get:Rule instead
    @get:Rule var activityTestRule = ActivityTestRule(
            CalculatorActivity::class.java,
            true, //initial touch mode
            false //lazily launch activity
    )

    @Test fun verifyVehiclePrice() {
        startActivity()
        registerIdlingResource()

        verifyViewText(R.id.tv_vehicle_price , "$48,801"               )
        verifyViewText(R.id.tv_payment_amount,  "$2,252* Monthly"      )
        verifyViewText(R.id.tv_term          ,      "24 Months,"       )
        verifyViewText(R.id.tv_rate          ,       "9.99% Financing" )
        verifyViewText(R.id.tv_min_term      ,      "24 mo"            )
        verifyViewText(R.id.tv_max_term      ,      "72 mo"            )

        unregisterIdlingResource()
    }

    private fun verifyViewText(@IdRes viewId: Int, text: String) {
        onView(withId(viewId)).check(matches(withText(text)))
    }

    private fun startActivity() {
        val intent = Intent()
        activityTestRule.launchActivity(intent)
    }

    private fun registerIdlingResource() {
        Espresso.registerIdlingResources(
                activityTestRule.activity.getCountingIdlingResource())
    }

    private fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                activityTestRule.activity.getCountingIdlingResource())
    }
}