package pl.ches.citybikes.data.api.nb

import pl.ches.citybikes.data.api.model.ApiNbCountriesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface NextBikeApi {

  @GET(NextBikeApiConsts.Endpoint.MAIN)
  fun getCountries(): Observable<ApiNbCountriesResponse>

  @GET(NextBikeApiConsts.Endpoint.MAIN)
  fun getCity(@Query("city") cityId: String): Observable<ApiNbCountriesResponse>

  companion object {
    const val FOR = "NextBikeApi"
  }

}