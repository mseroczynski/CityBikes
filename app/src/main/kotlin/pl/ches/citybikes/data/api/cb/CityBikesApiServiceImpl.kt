package pl.ches.citybikes.data.api.cb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import rx.Observable
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class CityBikesApiServiceImpl
constructor(private val cityBikesApi: CityBikesApi) : CityBikesApiService {

  override fun getAreas(): Observable<List<Area>> =
      cityBikesApi.getNetworks().map {
        val areas = ArrayList<Area>()
        it.networks
            .filter { !(it.companies?.contains(UNUSED_COMPANY) ?: false) }
            .forEach {
              areas.add(Area(
                  id = "${SOURCE_API.toPrefix}${it.id}",
                  sourceApi = SOURCE_API,
                  originalId = it.id,
                  originalName = it.name,
                  latitude = it.location.latitude,
                  longitude = it.location.longitude))
            }
        areas
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
    private val SOURCE_API = SourceApi.CITY_BIKES

    /**
     * Some of the areas on API aren't used as better data source is used for them
     */
    private val UNUSED_COMPANY = "Nextbike GmbH"

  }

}

