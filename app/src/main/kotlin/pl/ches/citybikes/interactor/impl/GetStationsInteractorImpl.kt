package pl.ches.citybikes.interactor.impl

import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.api.nb.NextBikeApiService
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.di.qualifier.Job
import pl.ches.citybikes.di.qualifier.PostJob
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.GetStationsResult
import pl.ches.citybikes.interactor.GetStationsResultType
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
constructor(@Job jobScheduler: Scheduler,
            @PostJob postJobScheduler: Scheduler,
            private val cityBikesApiService: CityBikesApiService,
            private val nextBikeApiService: NextBikeApiService) : GetStationsInteractor(jobScheduler,
    postJobScheduler) {

  override fun createObservable(param: List<Area>): Observable<GetStationsResult> {
    val observables = ArrayList<Observable<List<Station>>>().apply {
      param.forEach {
        add(when (it.sourceApi) {
          SourceApi.CITY_BIKES -> cityBikesApiService.getStations(it.originalId)
          SourceApi.NEXT_BIKE -> nextBikeApiService.getStations(it.originalId)
          else -> throw IllegalArgumentException("Source api must be determined!")
        })
      }
    }

    val zipFunc = FuncN { ArrayList<Station>().apply { it.forEach { addAll(it as List<Station>) } } }

    return Observable.zip(observables, zipFunc)
        .flatMap(nextObs(), errorObs(), { Observable.empty() })
  }

  private fun nextObs(): ((List<Station>) -> Observable<GetStationsResult>) = {
    Observable.just(GetStationsResult(GetStationsResultType.SUCCESS, it))
  }

  private fun errorObs(): (Throwable) -> Observable<GetStationsResult> = {
    Observable.just(GetStationsResult(GetStationsResultType.ERROR))
  }

}
