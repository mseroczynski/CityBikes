package pl.ches.citybikes.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
data class ApiCbNetwork(

    @SerializedName("href")
    var href: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("location")
    var location: ApiCbLocation,
    @SerializedName("name")
    var name: String,
    @SerializedName("stations")
    var stations: List<ApiCbStation>

)
