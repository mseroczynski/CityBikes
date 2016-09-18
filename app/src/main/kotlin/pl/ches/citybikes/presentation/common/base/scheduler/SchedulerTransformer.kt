package pl.ches.citybikes.presentation.common.base.scheduler

import rx.Observable

/**
 * A [Observable.Transformer] that is used to set the schedulers for an Observable that can
 * be subscribed by [MvpLceRxPresenter].
 *
 * @author Hannes Dorfmann
 * *
 * @since 1.0.0
 */
interface SchedulerTransformer<T> : Observable.Transformer<T, T>