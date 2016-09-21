package pl.ches.citybikes.testing.doubles

import android.location.Location

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object Fakes {

  val location by lazy {
    return@lazy Location("test")
  }

  val string by lazy {
    return@lazy "test_string"
  }

}