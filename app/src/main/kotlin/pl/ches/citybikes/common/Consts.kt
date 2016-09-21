package pl.ches.citybikes.common

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class Consts {

  object Config {
    const val OK_HTTP_API_TIMEOUT_IN_S = 20L
    const val INITIAL_RADIUS_IN_KM = 3.0
  }

  object Prefs {
    const val FILE_NAME_CACHE_PREFS = "cache_prefs"

    const val PREF_KEY_LAST_AREAS_IDS = "last_areas"
    const val PREF_KEY_LAST_LOCATION = "last_location"
  }
}
