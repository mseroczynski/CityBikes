package pl.ches.citybikes.data.api.nb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import rx.Observable
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class NextBikeApiServiceImpl
constructor(private val nextBikeApi: NextBikeApi) : NextBikeApiService {

  override fun getAreas(): Observable<List<Area>> =
      nextBikeApi.getCountries().map {
        val areas = ArrayList<Area>()
        it.countries.forEach {
          it.cities.forEach {
            areas.add(Area(
                id = "${SOURCE_API.toPrefix}${it.uid}",
                sourceApi = SOURCE_API,
                originalId = it.uid,
                originalName = it.name,
                latitude = it.lat,
                longitude = it.lng))
          }
        }
        areas
      }

  override fun getStations(cityId: String): Observable<List<Station>> =
      nextBikeApi.getCity(cityId).map {
        val stations = ArrayList<Station>()
        it.countries.forEach {
          it.cities.forEach {
            it.places.forEach {
              stations.add(Station(
                  id = "${SOURCE_API.toPrefix}${it.uid}",
                  sourceApi = SOURCE_API,
                  originalId = it.uid,
                  originalName = it.name,
                  latitude = it.lat,
                  longitude = it.lng,
                  freeBikes = it.bikes
              ))
            }
          }
        }
        stations.filter { !FAKE_STATIONS_IDS.contains(it.originalId) }
      }

  companion object {
    private val SOURCE_API = SourceApi.NEXT_BIKE

    /**
     * Some of stations on API aren't real, for now their id's are stored here
     */
    private val FAKE_STATIONS_IDS = arrayListOf(
        "543238", // Trinity Park I
        "543239", // Trinity Park I 1
        "448566", // New City
        "448565"  // Nestle House
    )

  }

}

