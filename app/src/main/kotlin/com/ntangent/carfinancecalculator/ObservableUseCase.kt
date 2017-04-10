package com.ntangent.carfinancecalculator

import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

/**
 * Base class for creating use cases returning Observables.
 */
abstract class ObservableUseCase<T, Params> protected constructor (
        private val subscriptionSchedulerProvider: SubscriptionSchedulerProvider,
        private val postExecutionSchedulerProvider: PostExecutionSchedulerProvider) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildObservable(params: Params): Observable<T>

    fun execute(params: Params, observer: DisposableObserver<T>) {
        disposables.add(buildObservable(params)
                .subscribeOn(subscriptionSchedulerProvider.getScheduler())
                .observeOn(postExecutionSchedulerProvider.getScheduler())
                .subscribeWith(observer))
    }

    fun unsubscribe() {
        disposables.clear()
    }
}