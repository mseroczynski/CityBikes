package pl.ches.citybikes.common

import pl.ches.citybikes.BuildConfig

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object DevHelper {

  // Config
  val RETROFIT_LOGS = debugOnly(false)
  val STETHO_LOGS = debugOnly(true)
  val LEAK_CANARY = debugOnly(false)

  fun debugOnly(value: Boolean): Boolean =
      if (BuildConfig.DEBUG) value
      else false

}