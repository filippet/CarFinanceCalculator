package com.ntangent.carfinancecalculator.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.addFragment(frameId: Int, fragment: Fragment) {
    val transaction = beginTransaction()
    with(transaction) {
        add(frameId, fragment)
        commit()
    }
}
