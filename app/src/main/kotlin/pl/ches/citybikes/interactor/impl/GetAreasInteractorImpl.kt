package pl.ches.citybikes.interactor.impl

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.repo.AreaRepository
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetAreasParam
import rx.Observable
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class GetAreasInteractorImpl
@Inject
constructor(schedulersProvider: SchedulersProvider,
            private val areaRepository: AreaRepository)
: GetAreasInteractor(schedulersProvider) {

  override fun createObservable(param: GetAreasParam): Observable<List<Area>> {
    return areaRepository.get(param.sourceApi, param.forceRefresh)
  }

}