package pl.ches.citybikes.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
data class ApiCbNetworksResponse(

    @SerializedName("networks")
    var networks: List<ApiCbNetwork>

)

data class ApiCbNetworkResponse(

    @SerializedName("network")
    var network: ApiCbNetwork

)
