package pl.ches.citybikes.testing.doubles

import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.GetStationsParam
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class GetStationsInteractorStub(schedulersProvider: SchedulersProvider) : GetStationsInteractor(schedulersProvider) {

  var stations: List<Station> = Fakes.stations

  override fun createObservable(param: GetStationsParam): Observable<List<Station>> = Observable.just(stations)

}