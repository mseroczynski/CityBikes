package pl.ches.citybikes.mvp.common.base

import android.support.v4.widget.SwipeRefreshLayout
import pl.ches.citybikes.R

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MvpLceUtil {

  companion object {
    fun showLceLoading(pullToRefresh: Boolean, contentView: SwipeRefreshLayout) {
      if (pullToRefresh && !contentView.isRefreshing) {
        // Workaround for measure bug: https://code.google.com/p/android/issues/detail?id=77712
        contentView.post { contentView.isRefreshing = true }
      }
    }

    fun stopLceLoading(contentView: SwipeRefreshLayout) {
      if (contentView.isRefreshing) {
        contentView.isRefreshing = false
      }
    }

    fun setUpSwipeRefreshLayColors(srl: SwipeRefreshLayout) {
      srl.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3)
    }
  }
}
