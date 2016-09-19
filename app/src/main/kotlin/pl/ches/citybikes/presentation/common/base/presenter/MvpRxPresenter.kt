package pl.ches.citybikes.presentation.common.base.presenter

import android.support.annotation.VisibleForTesting
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import pl.ches.citybikes.presentation.common.base.scheduler.AndroidSchedulerTransformer
import pl.ches.citybikes.presentation.common.base.scheduler.TestSchedulerTransformer
import rx.Observable
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class MvpRxPresenter<V : MvpView> : MvpNullObjectBasePresenter<V>() {

  protected var subscriptions: CompositeSubscription? = null
  @VisibleForTesting var useTestScheduler: Boolean = false

  protected fun <T> applyScheduler(): Observable.Transformer<T, T> =
      if (useTestScheduler) TestSchedulerTransformer() else AndroidSchedulerTransformer()

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
