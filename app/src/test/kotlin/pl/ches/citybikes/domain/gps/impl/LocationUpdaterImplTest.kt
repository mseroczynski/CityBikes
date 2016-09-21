package pl.ches.citybikes.domain.gps.impl

import org.junit.Before
import org.junit.Test
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.ches.citybikes.MockitoTest
import pl.ches.citybikes.data.prefs.CachePrefs
import pl.ches.citybikes.testing.doubles.Fakes
import pl.ches.citybikes.testing.extensions.any
import pl.ches.citybikes.testing.extensions.doReturn
import pl.ches.citybikes.testing.extensions.mock
import pl.ches.citybikes.testing.extensions.verify

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class LocationUpdaterImplTest : MockitoTest() {

  private lateinit var reactiveLocationProviderMock: ReactiveLocationProvider
  private lateinit var cachePrefsMock: CachePrefs
  private lateinit var sut: LocationUpdaterImpl

  @Before
  fun setUp() {
    reactiveLocationProviderMock = mock()
    cachePrefsMock = mock()
    sut = LocationUpdaterImpl(testSchedulersProvider, reactiveLocationProviderMock, cachePrefsMock)
  }

  @Test
  fun startUpdating_shouldCacheLocation() {
    doReturn(justObs(Fakes.location)).`when`(reactiveLocationProviderMock).getUpdatedLocation(any())

    sut.startUpdating()

    verify(cachePrefsMock).lastLocation = any()
  }



}