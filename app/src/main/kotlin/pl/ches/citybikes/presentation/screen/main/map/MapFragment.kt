package pl.ches.citybikes.presentation.screen.main.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.base.view.BaseFragment
import pl.ches.citybikes.presentation.util.MapsUtils
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MapFragment : BaseFragment<MapView, MapPresenter>(), MapView {

  private val component = App.component().plusMap()

  private lateinit var googleMap: GoogleMap
  private lateinit var iconsCache: HashMap<Int, BitmapDescriptor>
  private var userMarker: Marker? = null

  //region Setup
  override val layoutRes by lazy { R.layout.fragment_map }

  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): MapPresenter = component.presenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    map.onCreate(savedInstanceState)
    map.getMapAsync { googleMap ->
      this.googleMap = googleMap
      initIconsCache()
      presenter.mapReady()
    }
  }

  override fun onResume() {
    super.onResume()
    map.onResume()
  }

  override fun onStart() {
    super.onStart()
    map.onStart()
  }

  override fun onStop() {
    super.onStop()
    map.onStop()
  }

  override fun onPause() {
    super.onPause()
    map.onPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    map.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    map.onLowMemory()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    map.onSaveInstanceState(outState)
  }
  //endregion

  //region View
  override fun updateUserLocation(latLng: LatLng) =
      when (userMarker) {
        null -> initUserMarker(latLng)
        else -> updateUserMarker(latLng)
      }

  override fun updateStations(stations: List<Station>) = stations.forEach {
    initStationMarker(it)
  }

  //endregion

  private fun initUserMarker(latLng: LatLng) {
    userMarker = googleMap.addMarker(MarkerOptions().position(latLng).icon(iconsCache[KEY_USER_ICON]))
    MapsUtils.setCamera(googleMap, latLng)
  }

  private fun updateUserMarker(latLng: LatLng) = MapsUtils.animateMarker(googleMap, userMarker!!, latLng)

  private fun initStationMarker(station: Station): Marker {
    val latLng = LatLng(station.latitude, station.longitude)

    val bitmapDescriptor: BitmapDescriptor
    val freeBikes = station.freeBikes
    when (freeBikes) {
      "0",
      "1",
      "2",
      "3",
      "4" -> bitmapDescriptor = iconsCache[freeBikes.toInt()]!!
      else -> bitmapDescriptor = iconsCache[5]!!
    }

    val markerOptions = MarkerOptions()
        .position(latLng)
        .title(station.originalName)
        .snippet(station.freeBikes)
        .icon(bitmapDescriptor)

    return googleMap.addMarker(markerOptions)
  }

  private fun initIconsCache() {
    iconsCache = HashMap()
    iconsCache.put(0, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_empty))
    iconsCache.put(1, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_1))
    iconsCache.put(2, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_2))
    iconsCache.put(3, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_3))
    iconsCache.put(4, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_4))
    iconsCache.put(5, BitmapDescriptorFactory.fromResource(R.drawable.ic_bikes_full))
    iconsCache.put(KEY_USER_ICON, BitmapDescriptorFactory.fromResource(R.drawable.ic_you_on_map))
  }

  companion object {
    const val KEY_USER_ICON = 99

    fun newInstance() = MapFragment()
  }

}