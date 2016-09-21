package pl.ches.citybikes.presentation.screen.main.stations

import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.stations.StationsScout
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.presentation.common.base.presenter.MvpLceRxPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsPresenter
@Inject
constructor(private val schedulersProvider: SchedulersProvider,
            private val stationsScout: StationsScout,
            private val reactiveLocationProvider: ReactiveLocationProvider,
            private val getAreasInteractor: GetAreasInteractor) : MvpLceRxPresenter<StationsView, List<Station>>(schedulersProvider) {

  override fun attachView(view: StationsView) {
    super.attachView(view)
  }

  fun loadData(pullToRefresh: Boolean) {
    val currentStationsObs = stationsScout.currentSortedStationsObs(pullToRefresh)
//    val param = arrayListOf(Area("", SourceApi.NEXT_BIKE, "8", "", 0.0, 0.0))
//    subscribeLce(getStationsInteractor.createObservable(GetStationsParam(param, pullToRefresh)), pullToRefresh)
    subscribeLce(currentStationsObs, pullToRefresh)

//    val param = GetAreasParam(SourceApi.ANY, true)
//    val areasObs = getAreasInteractor.asObservable(param)
//    val lastKnownLocationObs = reactiveLocationProvider.lastKnownLocation

//    val locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(2000)
//    reactiveLocationProvider.getUpdatedLocation(locationRequest).compose(applyScheduler<Location>())
//        .subscribe({
//          v {""}
//        },{
//          v {""}
//        }, {
//          v {""}
//        })
//    val zipFunc = Func2<List<Area>, Location, List<Area>> { areas, lastKnownLocation ->
//      val radiusInKm = Consts.Config.INITIAL_RADIUS_IN_KM
//      val areasInRadiusOrClosest = getAreasInRadiusOrClosest(areas, lastKnownLocation, radiusInKm)
//      areasInRadiusOrClosest
//    }

//    areasObs.compose(applyScheduler<List<Area>>())
//    .subscribe({
//      v {""}
//    },{
//      v {""}
//    }, {
//      v {""}
//    })

//    Observable.zip(areasObs, lastKnownLocationObs, zipFunc).compose(applyScheduler<List<Area>>())
//    .subscribe({
//      v {""}
//    },{
//      v {""}
//    }, {
//      v {""}
//    })

  }

  //region Events
  fun stationClicked(position: Int) {
  }
  //endregion

}