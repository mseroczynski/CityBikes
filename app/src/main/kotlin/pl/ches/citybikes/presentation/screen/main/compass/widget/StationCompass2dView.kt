package pl.ches.citybikes.presentation.screen.main.compass.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import pl.ches.citybikes.domain.orientation.Orientation3d

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationCompass2dView
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0) : View(context, attrs, defStyle), StationCompass {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val path = Path()

  private var targetDrawable: Drawable? = null
  private var curOrientation: Orientation3d = Orientation3d(0F, 0F, 0F)
  private var targetAngle: Double = 0.0
  private var targetDist: Float = 0F

  init {
  }

  public override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    val size = Math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec))

    val center = size / 2F

    // TODO comment
    path.reset()
    path.fillType = Path.FillType.EVEN_ODD
    path.moveTo(center, (center / 8))
    path.lineTo((center * 15 / 20), (center / 3))
    path.lineTo(center, (center / 4))
    path.lineTo((center * 25 / 20), (center / 3))
    path.close()

    setMeasuredDimension(size, size)
  }

  override fun onDraw(canvas: Canvas) {
    val width: Int = width
    val height: Int = height
    val center: Float = width / 2F

    paint.textAlign = Paint.Align.CENTER

    paint.strokeWidth = center / 15

    paint.color = Color.WHITE
    paint.style = Style.FILL_AND_STROKE
    canvas.drawCircle(center, center, (center * 19 / 20), paint)
    paint.style = Style.STROKE

    paint.color = 0xFF33B5E5.toInt()

    canvas.drawCircle(center.toFloat(), center.toFloat(), (center * 19 / 20).toFloat(), paint)
    paint.setStrokeWidth(1f)

    paint.setColor(Color.BLACK)

    paint.textSize = (center * 2 / 5).toFloat()

    paint.style = Paint.Style.FILL_AND_STROKE
    canvas.drawText("targetAngle: ${Math.round(targetAngle)}°", center, center * 2 / 5, paint)
    paint.style = Paint.Style.STROKE

    canvas.rotate(-targetAngle.toFloat(), center.toFloat(), center.toFloat())

    paint.color = Color.GRAY

    paint.style = Paint.Style.FILL_AND_STROKE
    canvas.drawPath(path, paint)
    paint.style = Paint.Style.STROKE

    paint.textSize = (center / 5).toFloat()

    paint.style = Paint.Style.FILL_AND_STROKE
    canvas.drawText("N", center.toFloat(), (center * 9 / 20), paint)
    paint.style = Paint.Style.STROKE

    canvas.rotate(targetAngle.toFloat(), center, center)

    if (targetAngle != 0.0) {
      val y = center * 9 / 20
      val size = center / 8
      targetDrawable?.setBounds((center - size).toInt(), (y - size).toInt(), (center + size).toInt(), (y + size).toInt())
      targetDrawable?.draw(canvas)

      paint.color = Color.BLACK

      paint.style = Paint.Style.FILL_AND_STROKE
      canvas.drawPath(path, paint)
      paint.style = Paint.Style.STROKE
    }
  }

  //region StationCompass
  override fun setTargetDrawable(targetResId: Int) {
    targetDrawable = context.resources.getDrawable(targetResId, null)
  }

  override fun setOrientation3d(orientation: Orientation3d) {
    this.curOrientation = orientation
    invalidate()
  }

  override fun setAngleAndDistance(angle: Double, distance: Float) {
    targetAngle = angle
    targetDist = distance
    invalidate()
  }
  //endregion

}