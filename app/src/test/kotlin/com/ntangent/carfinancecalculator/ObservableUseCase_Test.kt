package com.ntangent.carfinancecalculator

import com.ntangent.carfinancecalculator.testutil.TestPostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.testutil.TestSubscriptionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.PostExecutionSchedulerProvider
import com.ntangent.carfinancecalculator.executor.SubscriptionSchedulerProvider
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test the creation of observable use cases.
 */
class ObservableUseCase_Test {

    private class FakeUseCase(
            subscriptionSchedulerProvider: SubscriptionSchedulerProvider,
            postExecutionSchedulerProvider: PostExecutionSchedulerProvider

    ) : ObservableUseCase<String, Int>(subscriptionSchedulerProvider, postExecutionSchedulerProvider) {
        override fun buildObservable(params: Int): Observable<String> {
            return Observable.just("")
        }
    }

    private class FakeObserver: DisposableObserver<String>() {
        var valueCount = 0

        override fun onNext(value: String) {
            ++valueCount
        }

        override fun onComplete() {
            // Do nothing
        }

        override fun onError(e: Throwable?) {
            // Do nothing
        }
    }

    private lateinit var testObserver: FakeObserver
    private lateinit var useCase: ObservableUseCase<String, Int>

    @Before fun setupMocksAndExecuteUseCase() {
        MockitoAnnotations.initMocks(this)
        testObserver = FakeObserver()
        useCase = FakeUseCase(TestSubscriptionSchedulerProvider(), TestPostExecutionSchedulerProvider())
        useCase.execute(0, testObserver)
    }

    @Test fun checkValues() {
        assertEquals(1, testObserver.valueCount)
    }

    @Test fun verifyIsDisposed() {
        useCase.unsubscribe()
        assertTrue(testObserver.isDisposed)
    }
}