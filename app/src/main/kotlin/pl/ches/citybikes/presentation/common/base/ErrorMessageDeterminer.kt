package pl.ches.citybikes.presentation.common.base

import android.content.Context
import pl.ches.citybikes.R
import java.net.UnknownHostException

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object ErrorMessageDeterminer {

  fun getErrorMessage(context: Context, e: Throwable, pullToRefresh: Boolean): String {
    var messageResId = R.string.toast_error
    if (e is UnknownHostException) {
      messageResId = R.string.toast_error
    }
    val sb = StringBuilder(context.getString(messageResId))
    if (!pullToRefresh) {
      val clickToRetry = context.getString(R.string.toast_error)
      sb.append(", ").append(clickToRetry)
    }
    return sb.toString()
  }

}