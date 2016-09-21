package pl.ches.citybikes.presentation.common.base.presenter

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView
import pl.ches.citybikes.domain.common.SchedulersProvider
import rx.Observable
import rx.Subscriber

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class MvpLceRxPresenter<V : MvpLceView<M>, M>(schedulersProvider: SchedulersProvider) : MvpRxPresenter<V>(schedulersProvider) {

  protected var subscriber: Subscriber<M>? = null

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

      override fun onError(e: Throwable) = this@MvpLceRxPresenter.onError(e, ptr)

      override fun onNext(m: M) = this@MvpLceRxPresenter.onNext(m)

      override fun onCompleted() = this@MvpLceRxPresenter.onCompleted()

    }

    obs = obs.compose(applyScheduler<M>())
    obs.subscribe(subscriber!!)
  }

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
    lceDataLoaded(data)
  }

  protected fun onError(e: Throwable, pullToRefresh: Boolean) {
    view.showError(e, pullToRefresh)
    // Callback pozwalający odsukbrybować jeśli ekran miałby być pusty
    if (!pullToRefresh) unsubscribeAll()
    unsubscribeLce()
  }

  protected fun onCompleted() {
    view.showContent()
    unsubscribeLce()
  }

  fun lceDataLoaded(data: M) {}

}