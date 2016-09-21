package pl.ches.citybikes.data.prefs.adapter

import android.content.SharedPreferences
import com.f2prateek.rx.preferences.Preference
import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.util.extensions.setString

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class LatLngPrefAdapter : Preference.Adapter<LatLng> {

  override fun get(key: String, sp: SharedPreferences): LatLng? {
    sp.getString(key, null)?.let {
      val vals = it.split(SEPARATOR)
      return LatLng(vals[0].toDouble(), vals[1].toDouble())
    }
    return null
  }

  override fun set(key: String, value: LatLng, editor: SharedPreferences.Editor) {
    editor.setString(key to "${value.latitude}$SEPARATOR${value.longitude}")
  }

  companion object {
    const val SEPARATOR = ":"
  }
}