package pl.ches.citybikes

import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.common.impl.SchedulersProviderImpl
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseTest {

  protected val testScheduler: Scheduler by lazy {
    return@lazy Schedulers.immediate()
  }

  protected val testSchedulersProvider: SchedulersProvider by lazy {
    return@lazy SchedulersProviderImpl(testScheduler, testScheduler)
  }

  protected fun <T> justObs(any: T?): Observable<T?> = Observable.just(any)

  protected fun <T> nullObs(): Observable<T> = Observable.just(null)

}