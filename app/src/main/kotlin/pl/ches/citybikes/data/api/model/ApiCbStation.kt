package pl.ches.citybikes.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
data class ApiCbStation(

    @SerializedName("empty_slots")
    var emptySlots: Int,
    @SerializedName("uid")
    var extra: ApiCbExtra,
    @SerializedName("free_bikes")
    var freeBikes: Int,
    @SerializedName("id")
    var id: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("name")
    var name: String

)
