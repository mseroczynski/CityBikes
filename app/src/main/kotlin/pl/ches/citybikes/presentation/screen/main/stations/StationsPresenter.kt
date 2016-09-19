package pl.ches.citybikes.presentation.screen.main.stations

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.GetStationsParam
import pl.ches.citybikes.presentation.common.base.presenter.MvpLceRxPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsPresenter
@Inject
constructor(private val getStationsInteractor: GetStationsInteractor) : MvpLceRxPresenter<StationsView, List<Station>>() {

  override fun attachView(view: StationsView) {
    super.attachView(view)
  }

  fun loadData(pullToRefresh: Boolean) {
    // TODO lokalizacja -> aktywne areas -> stacje
    val param = arrayListOf(Area("", SourceApi.NEXT_BIKE, "8", "", 0.0, 0.0))
    subscribeLce(getStationsInteractor.createObservable(GetStationsParam(param, pullToRefresh)), pullToRefresh)
  }

  //region Events
  fun stationClicked(position: Int) {
  }
  //endregion

}