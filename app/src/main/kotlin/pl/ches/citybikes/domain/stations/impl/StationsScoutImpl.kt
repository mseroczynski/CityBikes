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

  // TODO?
//  private val lastNonEmptyLocationObs: Observable<LatLng> by lazy {
//    return@lazy cachePrefs.lastLocationObs().filter { it != null }
//  }

  private val lastNonEmptyAreasIdsObs: Observable<List<String>> by lazy {
    return@lazy cachePrefs.lastAreasIdsObs().map { it.toList() }.filter { it.size > 0 }
  }

  //region StationsScout
  // TODO this should be just scouts subscription on other stream vals with combinelatest
  override fun currentSortedStationsObs(forceRefresh: Boolean): Observable<List<Pair<Station, Float>>> {
    val stationsObs = currentAreasObs(forceRefresh)
        .flatMap { areas ->
          val param = GetStationsParam(areas, forceRefresh)
          val stationsObs = getStationsInteractor.asObservable(param)
          stationsObs
        }

    val zipFunc = Func2<List<Station>, LatLng, List<Pair<Station, Float>>> { stations, lastLocation ->
      // Exclude duplicates, which may occur while app uses independent providers
      val stationsWithNoDuplicates = stations.distinctBy { it.originalName }
      // Calculate distance
      val stationsWithDistances = stationsWithNoDuplicates.map {
        it to gpsCalculator.getDistanceInMeters(lastLocation, LatLng(it.latitude, it.longitude))
      }
      // Sort
      stationsWithDistances.sortedBy { it.second }.take(Consts.Config.MAX_STATIONS_ON_LIST_COUNT)
    }

    val updatedLocationObs = cachePrefs.lastLocationObs().debounce(Consts.Config.DISTANCE_REFRESH_IN_S, TimeUnit.SECONDS)

//    return Observable.combineLatest(stationsObs, updatedLocationObs, zipFunc)
    return Observable.zip(stationsObs, updatedLocationObs, zipFunc)
  }
  //endregion

  private fun currentAreasObs(forceUpdatedLocationAreas: Boolean, forceFetchingAreas: Boolean = false): Observable<List<Area>> {
    val param = GetAreasParam(SourceApi.ANY, forceFetchingAreas)
    val areasObs = getAreasInteractor.asObservable(param)

    // Zips last known location and areas
    val freshZipFunc = Func2<List<Area>, LatLng, List<Area>> { areas, lastLocation ->
      val radiusInKm = Consts.Config.INITIAL_RADIUS_IN_KM
      val areasInRadiusOrClosest = getAreasInRadiusOrClosest(areas, lastLocation, radiusInKm)
      cachePrefs.lastAreasIds = areasInRadiusOrClosest.map { it.id }.toSet()
      areasInRadiusOrClosest
    }
    val freshObs = Observable.zip(areasObs, cachePrefs.lastLocationObs(), freshZipFunc)

    // Zips last known areas id's and areas
    val cacheZipFunc = Func2<List<Area>, List<String>, List<Area>> { areas, currentAreasIds ->
      areas.filter { currentAreasIds.contains(it.id) }
      areas
    }
    val cachedObs = Observable.zip(areasObs, lastNonEmptyAreasIdsObs, cacheZipFunc)

    // If no refresh of location is forced, we may use cached in prefs areas if they exist
    when (forceUpdatedLocationAreas) {
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