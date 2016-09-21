package pl.ches.citybikes.data.repo.impl

import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.api.nb.NextBikeApiService
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.AreaStore
import pl.ches.citybikes.data.repo.AreaRepository
import rx.Observable
import rx.functions.Func2
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class AreaRepositoryImpl
constructor(private val cityBikesApiService: CityBikesApiService,
            private val nextBikeApiService: NextBikeApiService,
            private val areaStore: AreaStore) : AreaRepository {

  override fun get(sourceApi: SourceApi, forceRefresh: Boolean): Observable<List<Area>> {
    when (forceRefresh) {
      true -> return netObs(sourceApi)
      false -> return Observable
          .concat(diskObs(sourceApi), netObs(sourceApi))
          .first { it != null && it.isNotEmpty() }
    }
  }

  private fun netObs(sourceApi: SourceApi): Observable<List<Area>> {
    return when (sourceApi) {
      SourceApi.CITY_BIKES -> cityBikesApiService.getAreas()
      SourceApi.NEXT_BIKE -> nextBikeApiService.getAreas()
      SourceApi.ANY -> Observable.zip(nextBikeApiService.getAreas(), cityBikesApiService.getAreas(),
          Func2 { nbList, cbList ->
            ArrayList<Area>().apply {
              nbList.forEach { add(it) }
              cbList.forEach { add(it) }
            }
          })
    }.doOnNext { areas ->
      areaStore.put(areas)
      areas
    }
  }

  private fun diskObs(sourceApi: SourceApi): Observable<List<Area>> = areaStore.getObs(sourceApi)

}