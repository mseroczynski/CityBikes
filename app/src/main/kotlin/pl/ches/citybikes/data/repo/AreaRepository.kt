package pl.ches.citybikes.data.repo

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface AreaRepository {

  fun get(sourceApi: SourceApi, forceRefresh: Boolean): Observable<List<Area>>

}