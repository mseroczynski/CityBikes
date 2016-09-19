package pl.ches.citybikes.data.repo

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface StationRepository {

  fun get(area: Area, forceRefresh: Boolean): Observable<List<Station>>

}