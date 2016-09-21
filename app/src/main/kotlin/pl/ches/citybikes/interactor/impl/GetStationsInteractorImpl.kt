package pl.ches.citybikes.interactor.impl

import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.repo.StationRepository
import pl.ches.citybikes.di.qualifier.Job
import pl.ches.citybikes.di.qualifier.PostJob
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.GetStationsParam
import rx.Observable
import rx.Scheduler
import rx.functions.FuncN
import java.util.*
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class GetStationsInteractorImpl
@Inject
constructor(schedulersProvider: SchedulersProvider,
            private val stationRepository: StationRepository)
: GetStationsInteractor(schedulersProvider) {

  override fun createObservable(param: GetStationsParam): Observable<List<Station>> {
    val observables = ArrayList<Observable<List<Station>>>().apply {
      param.areas.forEach {
        add(stationRepository.get(it, param.forceRefresh))
      }
    }

    val zipFunc = FuncN { ArrayList<Station>().apply { it.forEach { addAll(it as List<Station>) } } }

    return Observable.zip(observables, zipFunc)
  }

}
