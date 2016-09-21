package pl.ches.citybikes.domain.common.impl

import pl.ches.citybikes.domain.common.SchedulersProvider
import rx.Observable
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class SchedulersProviderImpl
constructor(override val job: Scheduler, override val postJob: Scheduler) : SchedulersProvider {

  override fun <T> apply(): Observable.Transformer<T, T> = Observable.Transformer<T, T> { obs ->
    obs
        .subscribeOn(job)
        .observeOn(postJob)
  }

}