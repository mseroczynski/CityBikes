package pl.ches.citybikes.data.api.nb

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface NextBikeApiService {

  fun getAreas(): Observable<List<Area>>

  fun getStations(cityId: String): Observable<List<Station>>

}