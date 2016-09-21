package pl.ches.citybikes.domain.gps.impl

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.domain.gps.GpsCalculator
import pl.ches.citybikes.domain.gps.internal.GeoLocation

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class GpsCalculatorImpl
constructor() : GpsCalculator {

  //region GpsCalculator
  override fun getBoundingCoords(latitude: Double, longitude: Double, radiusInKm: Double): Pair<LatLng, LatLng> {
    val boundingCoordinates = GeoLocation.fromDegrees(latitude, longitude).boundingCoordinates(radiusInKm, EARTH_RADIUS_IN_KM)
    return (boundingCoordinates[0].toLatLng() to boundingCoordinates[1].toLatLng())
  }

  override fun getDistanceInMeters(from: LatLng, to: LatLng): Float {
    val dist = FloatArray(1)
    Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, dist)
    return dist[0]
  }
  //endregion

  /**
   * Simple helper extension to keep GeoLocation free from framework dependencies
   */
  private fun GeoLocation.toLatLng(): LatLng = LatLng(this.latitudeInDegrees, this.longitudeInDegrees)

  companion object {
    const val EARTH_RADIUS_IN_KM = 6371.0
  }

}