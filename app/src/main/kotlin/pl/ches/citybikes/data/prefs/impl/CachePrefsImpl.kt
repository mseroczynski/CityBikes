package pl.ches.citybikes.data.prefs.impl

import com.f2prateek.rx.preferences.Preference
import com.f2prateek.rx.preferences.RxSharedPreferences
import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.common.Consts
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.data.prefs.adapter.LatLngPrefAdapter
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class CachePrefsImpl
constructor(private val rxPrefs: RxSharedPreferences) : CachePrefs {

  private val PREF_KEY_LAST_AREAS_IDS: String = Consts.Prefs.PREF_KEY_LAST_AREAS_IDS
  private val PREF_KEY_LAST_LOCATION: String = Consts.Prefs.PREF_KEY_LAST_LOCATION

  private val lastAreasPref: Preference<Set<String>>
  private val lastLocationPref: Preference<LatLng>

  init {
    lastAreasPref = rxPrefs.getStringSet(PREF_KEY_LAST_AREAS_IDS)
    lastLocationPref = rxPrefs.getObject(PREF_KEY_LAST_LOCATION, LatLngPrefAdapter())
  }

  override var lastAreasIds: Set<String>?
    get() = lastAreasPref.get()
    set(value) = lastAreasPref.set(value)

  override fun lastAreasIdsObs(): Observable<Set<String>> = lastAreasPref.asObservable().filter { it != null }

  override var lastLocation: LatLng?
    get() = lastLocationPref.get()
    set(value) = lastLocationPref.set(value)

  override fun lastLocationObs(): Observable<LatLng> = lastLocationPref.asObservable().filter { it != null }

}
