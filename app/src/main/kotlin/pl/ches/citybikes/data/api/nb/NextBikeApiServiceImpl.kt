package pl.ches.citybikes.data.api.nb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.di.scope.AppScope
import rx.Observable
import java.util.*
import javax.inject.Inject

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
        stations
      }

  companion object {
    val SOURCE_API = SourceApi.NEXT_BIKE
  }

}

