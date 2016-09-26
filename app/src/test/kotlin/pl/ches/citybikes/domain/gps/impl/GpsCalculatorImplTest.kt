package pl.ches.citybikes.domain.gps.impl

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import pl.ches.citybikes.MockitoTest

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class GpsCalculatorImplTest : MockitoTest() {

  private lateinit var sut: GpsCalculatorImpl

  @Before
  fun setUp() {
    sut = GpsCalculatorImpl()
  }

  @Test
  fun getBoundingCoords_shouldReturnCorrectlyCreateBoundingCoords() {
    val centerLat = 52.2155969
    val centerLng = 20.9793414
    val radius = 3.0
    val bounding1Lat = 52.18861725182244
    val bounding1Lng = 20.93530679108458
    val bounding2Lat = 52.24257654817755
    val bounding2Lng = 21.023376008915417

    val boundingCoords = sut.getBoundingCoords(centerLat, centerLng, radius)

    assertEquals(bounding1Lat, boundingCoords.first.latitude, ACCURACY_DELTA)
    assertEquals(bounding1Lng, boundingCoords.first.longitude, ACCURACY_DELTA)
    assertEquals(bounding2Lat, boundingCoords.second.latitude, ACCURACY_DELTA)
    assertEquals(bounding2Lng, boundingCoords.second.longitude, ACCURACY_DELTA)
  }

  companion object {
    const val ACCURACY_DELTA = 0.00000001
  }
}