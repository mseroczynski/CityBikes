package pl.ches.citybikes.presentation.screen.main

import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.gps.LocationUpdater
import pl.ches.citybikes.presentation.common.base.host.HostPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainPresenter
@Inject
constructor(schedulersProvider: SchedulersProvider, private val locationUpdater: LocationUpdater) : HostPresenter<MainView>(schedulersProvider) {

  override fun attachView(view: MainView?) {
    super.attachView(view)
    locationUpdater.initLocation()
    locationUpdater.startUpdating()
  }

  override fun detachView(retainInstance: Boolean) {
    locationUpdater.stopUpdating()
    super.detachView(retainInstance)
  }
}

