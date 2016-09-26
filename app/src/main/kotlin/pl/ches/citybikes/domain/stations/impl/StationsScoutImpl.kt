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
import java.util.concurrent.TimeUnit

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsScoutImpl
constructor(private val getAreasInteractor: GetAreasInteractor,
            private val getStationsInteractor: GetStationsInteractor,
            private val cachePrefs: CachePrefs,
            private val gpsCalculator: GpsCalculator) : StationsScout {

  //region StationsScout
  override fun currentSortedStationsObs(forceRefresh: Boolean): Observable<List<Pair<Station, Float>>> {
    // Creating stations observable
    val stationsObs = currentAreasObs(forceRefresh)
        .flatMap { areas ->
          val param = GetStationsParam(areas, forceRefresh)
          getStationsInteractor.asObservable(param)
        }

    // Zips cached & new locations with stations in areas
    val zipFunc = Func2<List<Station>, LatLng, List<Pair<Station, Float>>> { stations, lastLocation ->
      // Exclude duplicates (may occur while app uses independent providers)
      val stationsWithNoDuplicates = stations.distinctBy { it.originalName }
      // Calculate distance
      val stationsWithDistances = stationsWithNoDuplicates.map {
        it to gpsCalculator.getDistanceInMeters(lastLocation, LatLng(it.latitude, it.longitude))
      }
      // Sort
      stationsWithDistances.sortedBy { it.second }.take(Consts.Config.MAX_STATIONS_ON_LIST_COUNT)
    }

    return Observable.combineLatest(stationsObs, updatingLocationObs(Consts.Config.DISTANCE_REFRESH_IN_S), zipFunc)
  }

  override fun currentAreasObs(forceUpdatedLocationAreas: Boolean, forceFetchingAreas: Boolean): Observable<List<Area>> {
    // If no refresh of location is forced, we may use cached in prefs areas if they exist
    when (forceUpdatedLocationAreas) {
      true -> return initCurrentAreasObs(forceFetchingAreas)
      false -> return Observable.concat(cachedCurrentAreasObs(), initCurrentAreasObs(forceFetchingAreas)).first()
    }
  }
  //endregion

  private fun updatingLocationObs(timeInS: Long) = cachePrefs.lastLocationObs().debounce(timeInS, TimeUnit.SECONDS)

  private fun initCurrentAreasObs(forceFetchingAreas: Boolean): Observable<List<Area>> {
    // Creating areas observable
    val param = GetAreasParam(SourceApi.ANY, forceFetchingAreas)
    val areasObs = getAreasInteractor.asObservable(param)

    // Zips last known location and areas to current areas
    val freshZipFunc = Func2<List<Area>, LatLng, List<Area>> { areas, lastLocation ->
      val areasInRadiusOrClosest = getAreasInRadiusOrClosest(areas, lastLocation, Consts.Config.INITIAL_RADIUS_IN_KM)
      cachePrefs.lastAreasIds = areasInRadiusOrClosest.map { it.id }.toSet()
      areasInRadiusOrClosest
    }

    return Observable.zip(areasObs, cachePrefs.lastLocationObs(), freshZipFunc)
  }

  private fun cachedCurrentAreasObs(): Observable<List<Area>> {
    // Creating areas observable
    val param = GetAreasParam(SourceApi.ANY, false)
    val areasObs = getAreasInteractor.asObservable(param)

    // Zips last known areas id's and areas to current areas
    val cacheZipFunc = Func2<List<Area>, List<String>, List<Area>> { areas, currentAreasIds ->
      val currentCachedAreas = areas.filter { currentAreasIds.contains(it.id) }
      currentCachedAreas
    }

    return Observable.zip(areasObs, cachePrefs.lastAreasIdsObs().map { it.toList() }, cacheZipFunc)
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