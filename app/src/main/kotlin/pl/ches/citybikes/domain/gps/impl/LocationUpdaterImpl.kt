package pl.ches.citybikes.domain.gps.impl

import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.ches.citybikes.common.Consts
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.gps.LocationUpdater
import rx.Subscription
import v

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class LocationUpdaterImpl
constructor(private val schedulersProvider: SchedulersProvider,
            private val reactiveLocationProvider: ReactiveLocationProvider,
            private val cachePrefs: CachePrefs) : LocationUpdater {

  private var updatingSub: Subscription? = null

  private val locationRequest: LocationRequest by lazy {
    return@lazy LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(Consts.Config.LOCATION_UPDATE_INTERVAL_IN_MS)
  }

  //region LocationUpdater
  override fun initLocation() {
    updateAndCacheSub(true)
  }

  override fun startUpdating() {
    updatingSub = updateAndCacheSub(false)
  }

  override fun stopUpdating() {
    updatingSub?.let { it.unsubscribe() }
  }
  //endregion

  private fun updateAndCacheSub(single: Boolean): Subscription? = when (single) {
    true -> reactiveLocationProvider.lastKnownLocation
    false -> reactiveLocationProvider.getUpdatedLocation(locationRequest)
  }.compose(schedulersProvider.apply<Location>())
      .subscribe({
        v { "caching location update ${it.latitude}:${it.longitude}" }
        cachePrefs.lastLocation = LatLng(it.latitude, it.longitude)
      }, {
        // TODO ErrorMessageDeterminer -> popup with HostMediator?
      })

}