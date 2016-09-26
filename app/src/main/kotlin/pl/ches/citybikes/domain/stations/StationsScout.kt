package pl.ches.citybikes.domain.stations

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface StationsScout {

  /**
   * Returns current stations with calculated distances (and updates them)
   */
  fun currentSortedStationsObs(forceRefresh: Boolean): Observable<List<Pair<Station, Float>>>

  /**
   * Returns current areas (once)
   *
   * @param forceUpdatedLocationAreas will force redefining closest areas in cache
   * @param forceFetchingAreas will force fetching areas
   */
  fun currentAreasObs(forceUpdatedLocationAreas: Boolean, forceFetchingAreas: Boolean = false): Observable<List<Area>>

  }