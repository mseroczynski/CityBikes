package pl.ches.citybikes.presentation.common.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class FixedNestedScrollView : NestedScrollView {
  private var slop: Int = 0
  private var xDistance: Float = 0.toFloat()
  private var yDistance: Float = 0.toFloat()
  private var lastX: Float = 0.toFloat()
  private var lastY: Float = 0.toFloat()

  constructor(context: Context) : super(context) {
    init(context)
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(context)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(context)
  }

  private fun init(context: Context) {
    val config = ViewConfiguration.get(context)
    slop = config.scaledEdgeSlop
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    val x = ev.x
    val y = ev.y
    when (ev.action) {
      MotionEvent.ACTION_DOWN -> {
        xDistance = 0f
        yDistance = 0f
        lastX = ev.x
        lastY = ev.y

        // This is very important line that fixes
        computeScroll()
      }
      MotionEvent.ACTION_MOVE -> {
        val curX = ev.x
        val curY = ev.y
        xDistance += Math.abs(curX - lastX)
        yDistance += Math.abs(curY - lastY)
        lastX = curX
        lastY = curY

        if (xDistance > yDistance) {
          return false
        }
      }
    }

    return super.onInterceptTouchEvent(ev)
  }

}