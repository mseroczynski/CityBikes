package pl.ches.citybikes.data.repo.impl

import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.api.nb.NextBikeApiService
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.StationStore
import pl.ches.citybikes.data.repo.StationRepository
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationRepositoryImpl
constructor(private val cityBikesApiService: CityBikesApiService,
            private val nextBikeApiService: NextBikeApiService,
            private val stationStore: StationStore) : StationRepository {

  override fun get(area: Area, forceRefresh: Boolean): Observable<List<Station>> {
    when (forceRefresh) {
      true -> return netObs(area)
      false -> return Observable
          .concat(diskObs(area), netObs(area))
          .first { it != null && it.isNotEmpty() }
    }
  }

  private fun netObs(area: Area): Observable<List<Station>> {
    return when (area.sourceApi) {
      SourceApi.CITY_BIKES -> cityBikesApiService.getStations(area.originalId)
      SourceApi.NEXT_BIKE -> nextBikeApiService.getStations(area.originalId)
      else -> throw IllegalArgumentException("Source api must be determined!")
    }
        .doOnNext { stations ->
          stations.forEach { it.associate(area) }
          stationStore.put(stations)
          stations
        }
  }

  private fun diskObs(area: Area): Observable<List<Station>> = stationStore.getObs(area)

}