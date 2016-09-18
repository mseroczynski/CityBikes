package pl.ches.citybikes.interactor.impl

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.repo.AreaRepository
import pl.ches.citybikes.di.qualifier.Job
import pl.ches.citybikes.di.qualifier.PostJob
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetAreasResult
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class GetAreasInteractorImpl
@Inject
constructor(@Job jobScheduler: Scheduler,
            @PostJob postJobScheduler: Scheduler,
            private val areaRepository: AreaRepository)
: GetAreasInteractor(jobScheduler, postJobScheduler) {

  override fun createObservable(param: Unit?): Observable<GetAreasResult> {
    return areaRepository.get(SourceApi.NEXT_BIKE, true)
        .flatMap(nextObs())
  }

  private fun nextObs(): ((List<Area>) -> Observable<GetAreasResult>) = {
    Observable.just(GetAreasResult(it))
  }

}