package pl.ches.citybikes.presentation.screen.main.compass

import pl.ches.citybikes.domain.orientation.Orientation3d
import pl.ches.citybikes.presentation.common.base.host.HostedMvpView

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface CompassView : HostedMvpView {

  fun set3dMode(is3dMode: Boolean)

  fun setOrientation(orientation3d: Orientation3d)

  fun setAngleAndDistance(direction: Double, distance: Float)

}