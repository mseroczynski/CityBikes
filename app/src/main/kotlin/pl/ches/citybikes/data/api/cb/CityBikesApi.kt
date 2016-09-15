package pl.ches.citybikes.data.api.cb

import pl.ches.citybikes.data.api.model.ApiCbNetworkResponse
import pl.ches.citybikes.data.api.model.ApiCbNetworksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface CityBikesApi {

  @GET(CityBikesApiConsts.Endpoint.NETWORKS)
  fun getNetworks(): Observable<ApiCbNetworksResponse>

  @GET("${CityBikesApiConsts.Endpoint.NETWORKS}/{networkId}")
  fun getNetwork(@Path("networkId", encoded = true) networkId: String): Observable<ApiCbNetworkResponse>

  companion object {
    const val FOR = "CityBikesApi"
  }

}