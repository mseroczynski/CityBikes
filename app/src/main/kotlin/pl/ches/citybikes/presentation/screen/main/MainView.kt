package pl.ches.citybikes.presentation.screen.main

import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.base.host.HostView

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface MainView : HostView {

  fun showStationCompass(station: Station)

}