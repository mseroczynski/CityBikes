package pl.ches.citybikes.presentation.screen.main.compass.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import pl.ches.citybikes.common.DevHelper
import pl.ches.citybikes.domain.orientation.Orientation3d
import pl.ches.citybikes.presentation.common.util.UiUtils
import v

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationCompass3dView
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0) : View(context, attrs, defStyle), StationCompass {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val infoTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val targetArrowPath = Path()

  private var targetDrawable: Drawable? = null
  private var curOrientation: Orientation3d = Orientation3d(0F, 0F, 0F)
  private var targetAngle: Double = 0.0
  private var targetDist: Float = 0F
  private var is3dMode: Boolean = false

  // TODO Parametrize?
  private val bottomNavBarHeight = UiUtils.dp2px(context, 52F)
  // private val centerBtnHeight = UiUtils.dp2px(context, 36F)
  // private val radius = UiUtils.dp2px(context, 96F)
  private val textSize = UiUtils.dp2px(context, 12F)
  private val textLineSpacing = UiUtils.dp2px(context, 15F)
  private val gridUnit = UiUtils.dp2px(context, 8F)

  init {
    infoTextPaint.textAlign = Paint.Align.RIGHT
    infoTextPaint.textSize = textSize
  }

  public override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    super.onMeasure(widthSpec, heightSpec)
    when(is3dMode) {
      true -> {
        val center = measuredWidth / 2F

        // Target arrow
        targetArrowPath.reset()
        targetArrowPath.fillType = Path.FillType.EVEN_ODD
        targetArrowPath.moveTo(center, (center / 8 + center / 2))
        targetArrowPath.lineTo((center * 15 / 20), (center / 3 + center / 2))
        targetArrowPath.lineTo(center, (center / 4 + center / 2))
        targetArrowPath.lineTo((center * 25 / 20), (center / 3 + center / 2))
        targetArrowPath.close()
      }
      false -> {

      }
    }

  }

  override fun onDraw(canvas: Canvas) {
    val width: Int = width
    val height: Int = height
    val x = curOrientation.x // Current N angle
    val y = curOrientation.y
    val z = curOrientation.z // Azimuth
    val fixedX = if (x > 180) x - 360 else x
    val fixedY = if (y > 180) y - 360 else y

    val dAngle = Math.abs(x - targetAngle) // Angles difference
    val dist = Math.sqrt(dAngle * dAngle + fixedY * fixedY)

    if (DevHelper.DEBUG_COMPASS) {
      val rightInfoX = width - 2 * gridUnit
      val rightInfoY = height - bottomNavBarHeight - 2 * gridUnit
      val leftInfoX = 2 * gridUnit
      val leftInfoY = rightInfoY

      infoTextPaint.textAlign = Paint.Align.RIGHT
      canvas.drawText("x rounded: ${Math.round(x)}°", rightInfoX, rightInfoY, infoTextPaint)
      canvas.drawText("targetDist: ${Math.round(targetDist)} m", rightInfoX, rightInfoY - textLineSpacing, infoTextPaint)
      canvas.drawText("targetAngle: ${Math.round(targetAngle)}°", rightInfoX, rightInfoY - 2 * textLineSpacing, infoTextPaint)

      infoTextPaint.textAlign = Paint.Align.LEFT
      canvas.drawText("z: $z", leftInfoX, leftInfoY, infoTextPaint)
      canvas.drawText("y: $y", leftInfoX, leftInfoY - textLineSpacing, infoTextPaint)
      canvas.drawText("x: $x", leftInfoX, leftInfoY - 2 * textLineSpacing, infoTextPaint)
      canvas.drawText("fY: $fixedY", leftInfoX, leftInfoY - 3 * textLineSpacing, infoTextPaint)
      canvas.drawText("fX: $fixedX", leftInfoX, leftInfoY - 4 * textLineSpacing, infoTextPaint)
      canvas.drawText("dist: $dist", leftInfoX, leftInfoY - 5 * textLineSpacing, infoTextPaint)
      canvas.drawText("dAngle: $dAngle°", leftInfoX, leftInfoY - 6 * textLineSpacing, infoTextPaint)
    }

    // Arrow
    if (dist > 30) {
      var tmp = fixedX

//      while (Math.abs(targetAngle - x) < Math.abs(targetAngle - tmp)) {
//        tmp += 360f
//      }
//
//      while (Math.abs(targetAngle + x) < Math.abs(targetAngle - tmp)) {
//        tmp -= 360f
//      }

//      val r = -Math.toDegrees(Math.atan2(Math.toRadians(tmp - targetAngle), -Math.toRadians(fixedY.toDouble())))
//      canvas.rotate(r.toFloat(), (width / 2).toFloat(), (height / 2).toFloat())
//      paint.color = Color.BLACK
//
//      paint.style = Paint.Style.FILL
//      canvas.drawPath(targetArrowPath, paint)
//      paint.style = Paint.Style.STROKE
//
//      canvas.rotate((-r).toFloat(), (width / 2).toFloat(), (height / 2).toFloat())
    }

    paint.style = Style.FILL

    // If not far then draw circle
    if (dist <= 25) {
      paint.color = 0xFFFFFFFF.toInt()
      paint.alpha = (170 - dist * 6.8).toInt()

      canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), width * 0.45f, paint)

      paint.alpha = 255
    }

    canvas.translate(-x * width / 45, y * height / 45)

    paint.textSize = (width / 5).toFloat()

    paint.color = 0xAAFFFFFF.toInt()
    canvas.drawCircle((width / 2).toFloat(), (height / 2 - width / 15).toFloat(), (width / 5).toFloat(), paint)

    paint.textAlign = Paint.Align.CENTER
    paint.color = 0xFF000000.toInt()
    canvas.drawText("N", (width / 2).toFloat(), (height / 2).toFloat(), paint)

    canvas.translate((targetAngle * width / 45).toInt().toFloat(), 0f)

    targetDrawable?.let {
      val targetSizeHalf = (width / 4) - (dist * width / 180).toInt() // TODO Calibrate 180 -> 360
      v { "targetSizeHalf = $targetSizeHalf" }
      if (targetSizeHalf > 0) {
        it.setBounds(width / 2 - targetSizeHalf, height / 2 - targetSizeHalf, width / 2 + targetSizeHalf,
            height / 2 + targetSizeHalf)
        it.draw(canvas)
      }
    }
  }

  //region StationCompass
  override fun setTargetDrawable(targetResId: Int) {
    targetDrawable = context.resources.getDrawable(targetResId, null)
  }

  override fun setOrientation3d(orientation: Orientation3d) {
    this.curOrientation = orientation

    is3dMode = orientation.y > -55

    invalidate()
  }

  override fun setAngleAndDistance(angle: Double, distance: Float) {
    targetAngle = angle
    targetDist = distance
    invalidate()
  }
  //endregion

}

//    val halfWidth = width / 2F
//    val centerBtnY = height - UiUtils.dp2px(context, 36F).toFloat()
//    val radius = UiUtils.dp2px(context, 96F).toFloat()

// Bottom oval
//    paint.style = Style.FILL_AND_STROKE
//    paint.color = ContextCompat.getColor(context, R.color.white)
//    canvas.drawCircle(halfWidth, centerBtnY, radius, paint)

// Bottom oval N marker
//    paint.style = Style.STROKE
//    paint.color = ContextCompat.getColor(context, R.color.north)
//    paint.strokeWidth = width * 0.01F
//    ovalMarkerRect.set(halfWidth - radius, centerBtnY - radius, halfWidth + radius, centerBtnY + radius)
//    canvas.drawArc(ovalMarkerRect, -z - 95, 10F, false, paint)