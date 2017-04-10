package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.data.FinanceParams
import io.reactivex.Observable

/**
 * Created by filip on 4/10/17.
 */
interface FinanceParamsDataSource {
    fun getFinanceParams(): Observable<List<FinanceParams>>
}