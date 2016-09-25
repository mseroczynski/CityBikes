package pl.ches.citybikes.presentation.screen.main.stations

import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.stations.StationsScout
import pl.ches.citybikes.presentation.common.base.presenter.MvpLceRxPresenter
import v
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsPresenter
@Inject
constructor(private val schedulersProvider: SchedulersProvider,
            private val stationsScout: StationsScout) : MvpLceRxPresenter<StationsView, List<Pair<Station, Float>>>(
    schedulersProvider) {

  override fun attachView(view: StationsView) {
    super.attachView(view)
  }

  fun loadData(pullToRefresh: Boolean) {
    val currentStationsObs = stationsScout.currentSortedStationsObs(pullToRefresh).first()
    subscribeLce(currentStationsObs, pullToRefresh)
  }

  override fun lceDataLoaded(data: List<Pair<Station, Float>>) {
    // TODO
    addSubscription(stationsScout.currentSortedStationsObs(false).compose(applyScheduler<List<Pair<Station, Float>>>())
        .subscribe({
          view.setData(it)
        }, {
          v { "" }
        }, {
          v { "" }
        }))
  }

  //region Events
  fun stationClicked(position: Int) {
  }
  //endregion

}