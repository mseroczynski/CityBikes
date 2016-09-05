package pl.ches.citybikes.data.api.cb

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object CityBikesApiConsts {

  object Host {
    const val BASE_URL = "http://api.citybik.es/"
    const val API_VERSION = "v2"
  }

  object Endpoint {
    private const val PREFIX = "/${Host.API_VERSION}/"

    const val NETWORKS = "$PREFIX/networks"
  }

}