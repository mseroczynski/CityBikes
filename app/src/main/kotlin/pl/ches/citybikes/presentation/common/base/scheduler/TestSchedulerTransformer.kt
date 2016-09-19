package pl.ches.citybikes.presentation.common.base.scheduler

import rx.Observable
import rx.schedulers.Schedulers

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class TestSchedulerTransformer<T> : SchedulerTransformer<T> {

  override fun call(observable: Observable<T>): Observable<T> {
    return observable
        .subscribeOn(Schedulers.immediate())
        .observeOn(Schedulers.immediate())
  }

}