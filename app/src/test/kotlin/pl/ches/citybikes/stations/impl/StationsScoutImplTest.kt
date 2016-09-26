package pl.ches.citybikes.stations.impl

import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test
import pl.ches.citybikes.MockitoTest
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.domain.gps.GpsCalculator
import pl.ches.citybikes.domain.stations.StationsScout
import pl.ches.citybikes.domain.stations.impl.StationsScoutImpl
import pl.ches.citybikes.testing.doubles.Fakes
import pl.ches.citybikes.testing.doubles.GetAreasInteractorStub
import pl.ches.citybikes.testing.doubles.GetStationsInteractorStub
import pl.ches.citybikes.testing.extensions.*
import rx.observers.TestSubscriber

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsScoutImplTest : MockitoTest() {

  private lateinit var getAreasInteractorStub: GetAreasInteractorStub
  private lateinit var getStationsInteractorStub: GetStationsInteractorStub
  private lateinit var cachePrefsMock: CachePrefs
  private lateinit var gpsCalculatorMock: GpsCalculator
  private lateinit var sut: StationsScout

  @Before
  fun setUp() {
    getAreasInteractorStub = GetAreasInteractorStub(testSchedulersProvider)
    getStationsInteractorStub = GetStationsInteractorStub(testSchedulersProvider)
    cachePrefsMock = mock()
    gpsCalculatorMock = mock()

    doReturn(nullObs<Set<String>>()).`when`(cachePrefsMock).lastAreasIdsObs()

    sut = StationsScoutImpl(getAreasInteractorStub, getStationsInteractorStub, cachePrefsMock, gpsCalculatorMock)
  }

  @Test
  fun currentAreasObs_shouldCacheLastAreasIds_whenForceUpdatedLocationAreasTrue() {
    doReturn(justObs(Fakes.latLng)).`when`(cachePrefsMock).lastLocationObs()
    doReturn((LatLng(0.0, 0.0) to LatLng(40.0, 40.0))).`when`(gpsCalculatorMock).getBoundingCoords(any(), any(), any())

    val testSubscriber = TestSubscriber<List<Area>>()
    sut.currentAreasObs(true, false).subscribe(testSubscriber)

    testSubscriber.assertNoErrors()
    testSubscriber.assertCompleted()
    verify(cachePrefsMock).lastAreasIds = any()
  }

  @Test
  fun currentAreasObs_shouldUseCachedLastAreasIds_whenForceUpdatedLocationAreasFalse() {
    val lastAreasIds = setOf(Fakes.areas[0].id, Fakes.areas[3].id)
    doReturn(justObs(lastAreasIds)).`when`(cachePrefsMock).lastAreasIdsObs()

    val testSubscriber = TestSubscriber<List<Area>>()
    sut.currentAreasObs(false, false).subscribe(testSubscriber)

    testSubscriber.assertNoErrors()
    testSubscriber.assertCompleted()
    testSubscriber.assertValue(listOf(Fakes.areas[0], Fakes.areas[3]))
  }

  @Test
  fun currentAreasObs_shouldReturnClosestAreas_whenForceUpdatedLocationAreasTrue() {
    doReturn(justObs(LatLng(3.0, 3.0))).`when`(cachePrefsMock).lastLocationObs()
    doReturn((LatLng(2.0, 2.0) to LatLng(4.0, 4.0))).`when`(gpsCalculatorMock).getBoundingCoords(any(), any(), any())
    val closeArea = Area(id = "test_id_close", latitude = 3.0, longitude = 3.0)
    val farArea = Area(id = "test_id_far", latitude = 1.0, longitude = 4.0)

    getAreasInteractorStub.areas = listOf(closeArea, farArea)

    val testSubscriber = TestSubscriber<List<Area>>()
    sut.currentAreasObs(true, false).subscribe(testSubscriber)

    testSubscriber.assertNoErrors()
    testSubscriber.assertCompleted()
    testSubscriber.assertValue(listOf(closeArea))
  }

  @Test
  fun currentSortedStationsObs_shouldReturnStationsWithDistances() {
    doReturn(justObs(Fakes.latLng)).`when`(cachePrefsMock).lastLocationObs()
    doReturn((LatLng(0.0, 0.0) to LatLng(100.0, 100.0))).`when`(gpsCalculatorMock).getBoundingCoords(any(), any(), any())
    doReturn(1F).`when`(gpsCalculatorMock).getDistanceInMeters(any(), any())

    val testSubscriber = TestSubscriber<List<Pair<Station, Float>>>()
    sut.currentSortedStationsObs(true).subscribe(testSubscriber)

    testSubscriber.assertNoErrors()
    testSubscriber.assertCompleted()
    testSubscriber.assertValue(Fakes.stations.map { Pair(it, 1F) })
  }

}