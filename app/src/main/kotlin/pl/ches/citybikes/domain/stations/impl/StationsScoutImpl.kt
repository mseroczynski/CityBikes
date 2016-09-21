package pl.ches.citybikes.domain.stations.impl

import com.google.android.gms.maps.model.LatLng
import pl.ches.citybikes.common.Consts
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.domain.gps.GpsCalculator
import pl.ches.citybikes.domain.stations.StationsScout
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetAreasParam
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.GetStationsParam
import rx.Observable
import rx.functions.Func2

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsScoutImpl
constructor(private val getAreasInteractor: GetAreasInteractor,
            private val getStationsInteractor: GetStationsInteractor,
            private val cachePrefs: CachePrefs,
            private val gpsCalculator: GpsCalculator) : StationsScout {

  //region StationsScout
  override fun currentSortedStationsObs(forceRefresh: Boolean): Observable<List<Station>> {
    return currentAreasObs(forceRefresh, forceRefresh) // TODO More params?
        .flatMap { areas ->
          val param = GetStationsParam(areas, forceRefresh)
          val stationsObs = getStationsInteractor.asObservable(param)
          stationsObs
        }
  }
  //endregion

  private fun currentAreasObs(forceUpdatedLocation: Boolean, forceRefreshAreas: Boolean): Observable<List<Area>> {
    val param = GetAreasParam(SourceApi.ANY, forceRefreshAreas)
    val areasObs = getAreasInteractor.asObservable(param)
    val lastKnownLocationObs = cachePrefs.lastLocationObs()
    val currentAreasIdsObs = cachePrefs.currentAreasIdsObs().map { it.toList() }.filter { it.size > 0 }

    // Combines all areas with location to provide nearest Areas
    val freshZipFunc = Func2<List<Area>, LatLng, List<Area>> { areas, lastLocation ->
      val radiusInKm = Consts.Config.INITIAL_RADIUS_IN_KM
      val areasInRadiusOrClosest = getAreasInRadiusOrClosest(areas, lastLocation, radiusInKm)
      cachePrefs.lastAreasIds = areasInRadiusOrClosest.map { it.id }.toSet()
      areasInRadiusOrClosest
    }

    // Retrieves last saved Areas
    val cacheZipFunc = Func2<List<Area>, List<String>, List<Area>> { areas, currentAreasIds ->
      areas.filter { currentAreasIds.contains(it.id) }
    }

    val cachedObs = Observable.zip(areasObs, currentAreasIdsObs, cacheZipFunc)
    val freshObs = Observable.zip(areasObs, lastKnownLocationObs, freshZipFunc)

    // If no refresh of location is forced, we may use cached in prefs areas if they exist
    when (forceUpdatedLocation) {
      true -> return freshObs
      false -> return Observable
          .concat(cachedObs, freshObs)
          .first { it != null && it.isNotEmpty() }
    }
  }

  private fun getAreasInRadiusOrClosest(areas: List<Area>, latLng: LatLng, radiusInKm: Double): List<Area> {
    // Fail immediately if searching in empty collection
    check(areas.size > 0)

    val boundingCoords = gpsCalculator.getBoundingCoords(latLng.latitude, latLng.longitude, radiusInKm)

    val areasInSquare = areas
        .filter { it.latitude >= boundingCoords.first.latitude }
        .filter { it.latitude <= boundingCoords.second.latitude }
        .filter { it.longitude >= boundingCoords.first.longitude }
        .filter { it.longitude <= boundingCoords.second.longitude }
    if (!areasInSquare.isEmpty()) {
      val sortedAreas = areasInSquare.sortedBy {
        gpsCalculator.getDistanceInMeters(latLng, LatLng(it.latitude, it.longitude))
      }
      return sortedAreas
    } else return getAreasInRadiusOrClosest(areas, latLng, radiusInKm + 1.5)
  }

}