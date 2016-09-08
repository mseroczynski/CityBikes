package pl.ches.citybikes.data.repo

import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.AreaStore
import pl.ches.citybikes.di.scope.AppScope
import rx.Observable
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class AreaRepository
@Inject
constructor(private val cityBikesApiService: CityBikesApiService, private val areaStore: AreaStore) {

  fun get(sourceApi: SourceApi, forceRefresh: Boolean): Observable<List<Area>> {
    when (forceRefresh) {
      true -> return netObs(sourceApi)
      false -> return Observable
          .concat(diskObs(sourceApi), netObs(sourceApi))
          .first { it != null }
    }
  }

  private fun netObs(sourceApi: SourceApi): Observable<List<Area>> {
    return when (sourceApi) {
      SourceApi.CITY_BIKES -> cityBikesApiService.getAreas()
      SourceApi.NEXT_BIKE -> TODO()
      SourceApi.ANY -> TODO()
    }.doOnNext { areas ->
      areaStore.put(areas)
    }
  }

  private fun diskObs(sourceApi: SourceApi): Observable<List<Area>> {
    return areaStore.getObs(sourceApi)
  }

}