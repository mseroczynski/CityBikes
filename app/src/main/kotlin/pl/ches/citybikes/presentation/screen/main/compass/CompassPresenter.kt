package pl.ches.citybikes.presentation.screen.main.compass

import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.gps.GpsCalculator
import pl.ches.citybikes.domain.orientation.Orientation3d
import pl.ches.citybikes.domain.orientation.OrientationManager
import pl.ches.citybikes.presentation.common.base.presenter.MvpRxPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class CompassPresenter
@Inject
constructor(private val schedulersProvider: SchedulersProvider,
            private val cachePrefs: CachePrefs,
            private val orientationManager: OrientationManager,
            private val gpsCalculator: GpsCalculator) : MvpRxPresenter<CompassView>(schedulersProvider) {

  //region Events
  fun initTarget(target: LatLng) {
    addSubscription(orientationManager.orientation3dObs().compose(applyScheduler<Orientation3d>())
        .map { it.y > -55 }
        .distinctUntilChanged()
        .subscribe({ is3dMode -> view.set3dMode(is3dMode) })
    )

    addSubscription(orientationManager.orientation3dObs().compose(applyScheduler<Orientation3d>())
        .subscribe({ view.setOrientation(it) })
    )

    addSubscription(cachePrefs.lastLocationObs().compose(applyScheduler<LatLng>())
        .subscribe({ location ->
          val angle = -gpsCalculator.getAngle(location, target)
          val distance = gpsCalculator.getDistanceInMeters(location, target)
          view.setAngleAndDistance(angle, distance)
        })
    )
  }
  //endregion

}

