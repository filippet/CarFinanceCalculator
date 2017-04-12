package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import io.reactivex.Observable

interface FinanceParamsDataSource {
    fun getFinanceParams(): Observable<List<FinanceParams>>
}