package pl.ches.citybikes.data.prefs

import com.google.android.gms.maps.model.LatLng
import rx.Observable


/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface CachePrefs {

  var lastAreasIds: Set<String>?

  fun currentAreasIdsObs(): Observable<Set<String>>

  var lastLocation: LatLng?

  fun lastLocationObs(): Observable<LatLng>

}

