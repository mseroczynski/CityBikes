package pl.ches.citybikes.presentation.util

import android.os.Handler
import android.os.SystemClock
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object MapsUtils {

  fun animateMarker(map: GoogleMap, marker: Marker, toPosition: LatLng, hideMarker: Boolean = false) {
    val handler = Handler()
    val start = SystemClock.uptimeMillis()
    val proj = map.projection
    val startPoint = proj.toScreenLocation(marker.position)
    val startLatLng = proj.fromScreenLocation(startPoint)
    val duration: Long = 500

    val interpolator = LinearInterpolator()

    handler.post(object : Runnable {
      override fun run() {
        val elapsed = SystemClock.uptimeMillis() - start
        val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
        val lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude
        val lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude
        marker.position = LatLng(lat, lng)

        if (t < 1.0) {
          // Post again 16ms later.
          handler.postDelayed(this, 16)
        } else {
          if (hideMarker) {
            marker.isVisible = false
          } else {
            marker.isVisible = true
          }
        }
      }
    })
  }

  fun setCamera(map: GoogleMap, latLng: LatLng, zoom: Float = 14f) {
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
    map.moveCamera(cameraUpdate)
  }

}