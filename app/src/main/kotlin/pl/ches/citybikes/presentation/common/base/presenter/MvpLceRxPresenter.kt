package pl.ches.citybikes.presentation.common.base.presenter

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView
import pl.ches.citybikes.presentation.common.base.scheduler.AndroidSchedulerTransformer
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class MvpLceRxPresenter<V : MvpLceView<M>, M> : MvpNullObjectBasePresenter<V>() {

  protected var subscriber: Subscriber<M>? = null
  protected var subscriptions: CompositeSubscription? = null

  //region LCE
  /**
   * Called in [.subscribeLce] to set `subscribeOn()` and
   * `observeOn()`. As default it uses [AndroidSchedulerTransformer]. Override
   * this method if you want to provide your own scheduling implementation.
   */
  protected fun applyScheduler(observable: Observable<M>): Observable<M> {
    return observable.compose(AndroidSchedulerTransformer<M>())
  }

  /**
   * Subscribes the presenter himself as subscriber on the observable
   *
   * @param observable The observable to subscribeLce
   * @param pullToRefresh Pull to refresh?
   */
  fun subscribeLce(observable: Observable<M>, pullToRefresh: Boolean) {
    var obs = observable

    view.showLoading(pullToRefresh)

    unsubscribeLce()

    subscriber = object : Subscriber<M>() {
      private val ptr = pullToRefresh

      override fun onError(e: Throwable) {
        this@MvpLceRxPresenter.onError(e, ptr)
      }

      override fun onNext(m: M) {
        this@MvpLceRxPresenter.onNext(m)
      }

      override fun onCompleted() {
        this@MvpLceRxPresenter.onCompleted()
      }

    }

    obs = applyScheduler(obs)
    obs.subscribe(subscriber!!)
  }

  /**
   * Usuwa wyróżnioną subskrypcję LCE
   */
  protected fun unsubscribeLce() {
    if (subscriber != null && !subscriber!!.isUnsubscribed) {
      subscriber!!.unsubscribe()
    }

    subscriber = null
  }

  override fun detachView(retainInstance: Boolean) {
    super.detachView(retainInstance)
    if (!retainInstance) {
      unsubscribeLce()
      unsubscribeAll()
    }
  }

  protected fun onNext(data: M) {
    view.setData(data)
    dataLoaded(data)
  }

  protected fun onError(e: Throwable, pullToRefresh: Boolean) {
    view.showError(e, pullToRefresh)
    if (!pullToRefresh) unsubscribeAll()
    unsubscribeLce()
  }

  protected fun onCompleted() {
    view.showContent()
    unsubscribeLce()
  }
  //endregion

  //region MultiRx
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

  /**
   * Called when LCE subscription succeeds
   *
   * @param data presentation model
   */
  abstract fun dataLoaded(data: M)
  //endregion

}