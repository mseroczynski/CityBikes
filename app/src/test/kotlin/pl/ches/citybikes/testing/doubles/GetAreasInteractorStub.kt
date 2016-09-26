package pl.ches.citybikes.testing.doubles

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetAreasParam
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class GetAreasInteractorStub(schedulersProvider: SchedulersProvider) : GetAreasInteractor(schedulersProvider) {

  var areas: List<Area> = Fakes.areas

  override fun createObservable(param: GetAreasParam): Observable<List<Area>> = Observable.just(areas)

}