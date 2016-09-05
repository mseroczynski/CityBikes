package pl.ches.citybikes.data.api.cb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface CityBikesApiService {

  fun getAreas() : Observable<List<Area>>

  fun getStations(networkId: String): Observable<List<Station>>

}