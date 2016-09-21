package pl.ches.citybikes.domain.common

import rx.Observable
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface SchedulersProvider {

  val job: Scheduler

  val postJob: Scheduler

  fun <T> apply(): Observable.Transformer<T, T>

}