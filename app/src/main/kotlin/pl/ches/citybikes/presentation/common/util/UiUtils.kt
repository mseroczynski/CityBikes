package pl.ches.citybikes.presentation.common.util

import android.content.Context

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object UiUtils {

  fun dp2px(context: Context, dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
  }

}