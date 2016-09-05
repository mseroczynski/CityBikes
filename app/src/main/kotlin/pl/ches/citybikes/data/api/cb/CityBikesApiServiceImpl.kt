package pl.ches.citybikes.data.api.cb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.di.scope.AppScope
import rx.Observable
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class CityBikesApiServiceImpl
@Inject
constructor(private val cityBikesApi: CityBikesApi) : CityBikesApiService {

  override fun getAreas(): Observable<List<Area>> =
      cityBikesApi.getNetworks().map {
        it.networks.map {
          Area(
              id = "${SOURCE_API.toPrefix}${it.id}",
              sourceApi = SOURCE_API,
              originalId = "${it.id}",
              originalName = it.name,
              latitude = it.location.latitude,
              longitude = it.location.longitude)
        }
      }

  override fun getStations(networkId: String): Observable<List<Station>> =
      cityBikesApi.getNetwork(networkId).map {
        it.network.stations.map {
          Station(
              id = "${SOURCE_API.toPrefix}${it.id}",
              sourceApi = SOURCE_API,
              originalId = it.id,
              originalName = it.name,
              latitude = it.latitude,
              longitude = it.longitude,
              freeBikes = "${it.freeBikes}"
          )
        }

      }

  companion object {
    val SOURCE_API = SourceApi.CITY_BIKES
  }

}

