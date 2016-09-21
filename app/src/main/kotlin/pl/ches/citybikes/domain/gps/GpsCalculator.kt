package pl.ches.citybikes.domain.gps

import com.google.android.gms.maps.model.LatLng

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface GpsCalculator {

  fun getBoundingCoords(latitude: Double, longitude: Double, radiusInKm: Double): Pair<LatLng, LatLng>

  fun getDistanceInMeters(from: LatLng, to: LatLng): Float

}