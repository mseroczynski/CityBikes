package pl.ches.citybikes.domain.gps

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface LocationUpdater {

  fun initLocation()

  fun startUpdating()

  fun stopUpdating()

}