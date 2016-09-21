package pl.ches.citybikes.interactor.base

import pl.ches.citybikes.domain.common.SchedulersProvider
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.Subscription
import rx.functions.Action0
import rx.functions.Action1
import rx.internal.util.ActionSubscriber
import rx.internal.util.InternalObservableUtils
import rx.subscriptions.CompositeSubscription

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseInteractor<Param, Result>
protected constructor(private val schedulersProvider: SchedulersProvider) : Subscription {

  private val subscriptions: CompositeSubscription

  init {
    checkNotNull(schedulersProvider)
    subscriptions = CompositeSubscription()
  }

  fun asObservable(param: Param) = createObservable(param).compose(schedulersProvider.apply<Result>())

  fun execute(subscriber: Subscriber<in Result>, param: Param): Subscription {
    checkNotNull(subscriber)
    val subscribtion = asObservable(param).subscribe(subscriber)
    subscriptions.add(subscribtion)
    return subscribtion
  }

  fun execute(onNext: Action1<in Result>, param: Param): Subscription {
    checkNotNull(onNext)

    return execute(ActionSubscriber(onNext, InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Action0 {}), param)
  }

  fun execute(onNext: Action1<in Result>, onError: Action1<Throwable>, param: Param): Subscription {
    checkNotNull(onNext)
    checkNotNull(onError)

    return execute(ActionSubscriber(onNext, onError, Action0 {}), param)
  }

  fun execute(onNext: Action1<in Result>, onError: Action1<Throwable>, onCompleted: Action0, param: Param): Subscription {
    checkNotNull(onNext)
    checkNotNull(onError)
    checkNotNull(onCompleted)

    return execute(ActionSubscriber(onNext, onError, onCompleted), param)
  }

  override fun unsubscribe() {
    subscriptions.unsubscribe() // .clear()?
  }

  override fun isUnsubscribed(): Boolean {
    return !subscriptions.hasSubscriptions()
  }

  abstract fun createObservable(param: Param): Observable<Result>
}

