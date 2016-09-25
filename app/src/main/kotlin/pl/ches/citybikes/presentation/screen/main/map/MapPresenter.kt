package pl.ches.citybikes.presentation.screen.main.map

import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.stations.StationsScout
import pl.ches.citybikes.presentation.common.base.presenter.MvpRxPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MapPresenter
@Inject
constructor(private val schedulersProvider: SchedulersProvider,
            private val cachePrefs: CachePrefs,
            private val stationsScout: StationsScout) : MvpRxPresenter<MapView>(schedulersProvider) {

  override fun attachView(view: MapView) {
    super.attachView(view)
  }

  //region Events
  fun mapReady() {
    //
    addSubscription(cachePrefs.lastLocationObs().compose(applyScheduler<LatLng>())
        .subscribe { latLng ->
          view.updateUserLocation(latLng)
        })

    // (Re)Load stations once for every time areas are changed
//    val stationsObs = stationsScout.currentSortedStationsObs(false).first()
//    addSubscription(cachePrefs.lastAreasIdsObs().flatMap { stationsObs }.compose(applyScheduler<List<Pair<Station, Float>>>())

//    addSubscription(stationsScout.currentSortedStationsObs(false).first().compose(applyScheduler<List<Pair<Station, Float>>>())
    addSubscription(stationsScout.currentSortedStationsObs(false).compose(applyScheduler<List<Pair<Station, Float>>>())
        .subscribe { stations ->
          view.updateStations(stations.map { it.first })
        }
    )
  }
  //endregion

}