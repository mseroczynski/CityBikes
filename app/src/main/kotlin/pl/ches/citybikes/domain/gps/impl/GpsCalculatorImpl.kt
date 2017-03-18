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

  override fun getAngle(from: LatLng, to: LatLng): Double {
    val lat1 = from.latitude
    val lng1 = from.longitude
    val lat2 = to.latitude
    val lng2 = to.longitude
    val dLng = lng1 - lng2

    val directionRad = getDirectionRad(Math.toRadians(lat1), Math.toRadians(lat2), Math.toRadians(dLng))
    return Math.toDegrees(directionRad)
  }
  //endregion

  private fun getDirectionRad(lat1: Double, lat2: Double, dLng: Double) = Math.atan2(Math.sin(dLng),
      (Math.cos(lat1) * Math.tan(lat2)) - (Math.sin(lat1) * Math.cos(dLng)))

  /**
   * Simple helper extension to keep GeoLocation free from framework dependencies
   */
  private fun GeoLocation.toLatLng(): LatLng = LatLng(this.latitudeInDegrees, this.longitudeInDegrees)

  companion object {
    const val EARTH_RADIUS_IN_KM = 6371.0
  }

}