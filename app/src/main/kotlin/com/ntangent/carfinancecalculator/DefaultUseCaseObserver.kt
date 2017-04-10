package com.ntangent.carfinancecalculator

import io.reactivex.observers.DisposableObserver

/**
 * Utility class to facilitate the creation of DisposableObserver
 * by providing default implementation for DisposableObserver funtions.
 * That way the concrete observers can derive from this class and provide (override)
 * only the implementation they need.
 */
open class DefaultUseCaseObserver<T> : DisposableObserver<T>() {
    override fun onComplete() {
        // default do nothing
    }

    override fun onNext(value: T) {
        // default do nothing
    }

    override fun onError(e: Throwable?) {
        // default do nothing
    }
}