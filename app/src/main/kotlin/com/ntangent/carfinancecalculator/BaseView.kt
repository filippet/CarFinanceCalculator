package com.ntangent.carfinancecalculator

interface BaseView<T : BasePresenter> {
    fun setPresenter(presenter: T)
}