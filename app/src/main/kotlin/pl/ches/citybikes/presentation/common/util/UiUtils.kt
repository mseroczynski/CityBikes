package pl.ches.citybikes.presentation.common.util

import android.content.Context
import android.support.v4.content.ContextCompat
import pl.ches.citybikes.R

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object UiUtils {

  fun dp2px(context: Context, dp: Float): Float {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f)
  }

  fun getColor(context: Context, freeBikes: String): Int =
      when (freeBikes) {
        "0" -> ContextCompat.getColor(context, R.color.bikes_0)
        "1",
        "2",
        "3",
        "4" -> ContextCompat.getColor(context, R.color.bikes_1_to_4)
        else -> ContextCompat.getColor(context, R.color.bikes_5_or_more)
      }

  fun getResId(freeBikes: String): Int =
      when (freeBikes) {
        "0" -> R.drawable.ic_bikes_0
        "1" -> R.drawable.ic_bikes_1
        "2" -> R.drawable.ic_bikes_2
        "3" -> R.drawable.ic_bikes_3
        "4" -> R.drawable.ic_bikes_4
        else -> R.drawable.ic_bikes_5_or_more
      }

}