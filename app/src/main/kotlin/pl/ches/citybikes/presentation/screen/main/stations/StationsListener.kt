package pl.ches.citybikes.presentation.screen.main.stations

import pl.ches.citybikes.data.disk.entity.Station

/**
 * TODO może lepiej da się to zrobić?
 *
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface StationChooseListener {

  fun stationChosen(station: Station)

}