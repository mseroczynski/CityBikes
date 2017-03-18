package pl.ches.citybikes.domain.orientation

import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface OrientationManager {

  fun isSupported(): Boolean

  fun orientation3dObs(): Observable<Orientation3d>

}