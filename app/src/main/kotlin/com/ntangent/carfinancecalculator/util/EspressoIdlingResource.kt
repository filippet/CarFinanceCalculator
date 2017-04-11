package com.ntangent.carfinancecalculator.util

import android.support.test.espresso.IdlingResource

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"

    private val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }

    val idlingResource: IdlingResource
        get() = countingIdlingResource
}
