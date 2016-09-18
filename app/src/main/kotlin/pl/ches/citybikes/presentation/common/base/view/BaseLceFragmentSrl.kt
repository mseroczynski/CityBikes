package pl.ches.citybikes.presentation.common.base.view

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import pl.ches.citybikes.presentation.common.base.MvpLceUtil
import pl.ches.citybikes.presentation.common.base.host.HostedMvpLceView
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import pl.ches.citybikes.presentation.common.base.view.BaseLceFragment

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseLceFragmentSrl<CV : SwipeRefreshLayout, M, V : HostedMvpLceView<M>, P : MvpPresenter<V>> : BaseLceFragment<CV, M, V, P>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    MvpLceUtil.setUpSwipeRefreshLayColors(contentView)
  }

  override fun showLoading(pullToRefresh: Boolean) {
    super.showLoading(pullToRefresh)
    MvpLceUtil.showLceLoading(pullToRefresh, contentView)
  }

  override fun showContent() {
    super.showContent()
    MvpLceUtil.stopLceLoading(contentView)
  }

  override fun showError(e: Throwable, pullToRefresh: Boolean) {
    super.showError(e, pullToRefresh)
    MvpLceUtil.stopLceLoading(contentView)
  }
}
