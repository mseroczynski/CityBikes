package pl.ches.citybikes.mvp.common.base.scheduler

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * The default [SchedulerTransformer] that subscrubes on [Schedulers.newThread] and
 * observes on Android's main Thread.
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
class AndroidSchedulerTransformer<T> : SchedulerTransformer<T> {

  override fun call(observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
  }

}