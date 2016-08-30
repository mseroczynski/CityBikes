package pl.ches.citybikes.mvp.common.base.presenter

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import pl.ches.citybikes.mvp.common.base.scheduler.AndroidSchedulerTransformer
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class MvpRxPresenter<V : MvpView> : MvpNullObjectBasePresenter<V>() {

  protected var subscriptions: CompositeSubscription? = null

  protected fun <T> applyScheduler(): Observable.Transformer<T, T> {
    return AndroidSchedulerTransformer()
  }

  fun addSubscription(subscription: Subscription) {
    if (subscriptions == null || subscriptions!!.isUnsubscribed) {
      subscriptions = CompositeSubscription()
    }
    subscriptions!!.add(subscription)
  }

  protected fun unsubscribe(subscription: Subscription) {
    if (subscriptions != null) {
      subscriptions!!.remove(subscription)
    }
  }

  protected fun unsubscribeAll() {
    if (subscriptions != null && !subscriptions!!.isUnsubscribed) {
      subscriptions!!.unsubscribe()
    }
  }

  override fun detachView(retainInstance: Boolean) {
    super.detachView(retainInstance)
    if (!retainInstance) {
      unsubscribeAll()
    }
  }
}
