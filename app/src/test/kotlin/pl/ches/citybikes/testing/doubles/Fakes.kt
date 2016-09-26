package pl.ches.citybikes.testing.doubles

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object Fakes {

  val areas by lazy {
    ArrayList<Area>().apply {
      for (i in 0..5) {
        add(Area("test_id$i",
            SourceApi.ANY,
            "test_id$i",
            "test_name$i",
            1.2*i,
            3.4*i))
      }
    }
  }

  val stations by lazy {
    ArrayList<Station>().apply {
      for (i in 0..5) {
        add(Station("test_id$i",
            SourceApi.ANY,
            "test_id$i",
            "test_name$i",
            1.2,
            3.4,
            "12"))
      }
    }
  }

  val latLng by lazy {
    return@lazy LatLng(52.2155969, 20.9793414)
  }

  val location by lazy {
    return@lazy Location("test")
  }

  val string by lazy {
    return@lazy "test_string"
  }

}