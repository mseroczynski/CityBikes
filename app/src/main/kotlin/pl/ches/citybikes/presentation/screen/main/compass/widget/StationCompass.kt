package pl.ches.citybikes.presentation.screen.main.compass.widget

import android.support.annotation.IntegerRes
import pl.ches.citybikes.domain.orientation.Orientation3d

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface StationCompass {

  fun setTargetDrawable(@IntegerRes targetResId: Int)

  fun setOrientation3d(orientation: Orientation3d)

  fun setAngleAndDistance(angle: Double, distance: Float)

}