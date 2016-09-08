package pl.ches.citybikes.feature.gps

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.di.scope.AppScope
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class GpsHelper
@Inject
constructor() {

  fun getDistanceInMeters(startLat: Double, startLong: Double, endLat: Double, endLong: Double): Float {
    val dist = FloatArray(1)
    Location.distanceBetween(startLat, startLong, endLat, endLong, dist)
    return dist[0]
  }

  fun getDistanceInMeters(startLat: Double, startLong: Double, endLocation: Location): Float =
      getDistanceInMeters(startLat, startLong, endLocation.latitude, endLocation.longitude)

  fun getBoundingSquare(latLng: LatLng, distanceInKm: Double): Array<GeoLocation>
      = GeoLocation.fromDegrees(latLng.latitude, latLng.longitude).boundingCoordinates(distanceInKm, EARTH_RADIUS_IN_KM)

  companion object {
    const val EARTH_RADIUS_IN_KM = 6371.0
  }

}