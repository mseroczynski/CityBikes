package pl.ches.citybikes.presentation.screen.main.map

import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.base.host.HostedMvpView

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface MapView : HostedMvpView {

  fun updateUserMarker(latLng: LatLng)

  fun showStationMarkers(stations: List<Station>)

  fun clearStationsMarkers()

}