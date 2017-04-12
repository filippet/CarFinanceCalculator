package com.ntangent.carfinancecalculator.calculator

import com.ntangent.carfinancecalculator.BasePresenter
import com.ntangent.carfinancecalculator.BaseView
import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams

interface CalcItemContract {
    interface Presenter: BasePresenter

    interface View: BaseView<Presenter> {

        fun setFinanceParamsList(financeParamsList: List<FinanceParams>)
    }
}