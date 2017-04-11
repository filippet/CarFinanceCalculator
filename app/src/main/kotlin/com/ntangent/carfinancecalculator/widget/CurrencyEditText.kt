package com.ntangent.carfinancecalculator.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class CurrencyEditText
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null): EditText(context, attrs) {

    interface OnValueChangeListener {
        fun newValue(value: Int)
    }

    var value: Int = 0

    private var onValueChangeListener: OnValueChangeListener? = null

    init {
        initialize()
    }

    public fun subscribeOnValueChangeListener(listener: OnValueChangeListener) {
        onValueChangeListener = listener
    }

    public fun removeOnValueChangeListener() {
        onValueChangeListener = null
    }

    private fun initialize() {
        this.setOnFocusChangeListener {
            v, hasFocus ->
            if (hasFocus) {

            } else {
                val newValue = getAmount()
                if (newValue != value) {
                    value = newValue
                    notifyOnValueChangeListener()
                }
            }
        }
    }

    public fun getAmount(): Int {
        return convertToInt(this.text.toString())
    }

    private fun notifyOnValueChangeListener() {
        if (onValueChangeListener != null) {
            onValueChangeListener!!.newValue(value)
        }
    }

    private fun convertToInt(str: String): Int {
        return if (!str.isEmpty()) {
            str.toInt()
        } else {
            0
        }
    }
}