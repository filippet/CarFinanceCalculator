package com.ntangent.carfinancecalculator.data.source

import com.ntangent.carfinancecalculator.calculator.domain.FinanceParams
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

class FinanceParamsRepository(
        private val localDataSource: FinanceParamsDataSource
): FinanceParamsDataSource {

    override fun getFinanceParams(): Observable<List<FinanceParams>> {
        return Observable.create( ObservableOnSubscribe {
            emitter ->
            if (emitter.isDisposed) return@ObservableOnSubscribe

            localDataSource.getFinanceParams().subscribe(
                    { params -> emitter.onNext(params) }, // onNext
                    { err -> emitter.onError(err) },      // onError
                    { emitter.onComplete() }              // onComplete
            )
        })
    }
}