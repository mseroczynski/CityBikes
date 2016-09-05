package pl.ches.citybikes.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
data class ApiCbExtra(

    @SerializedName("status")
    var status: String,
    @SerializedName("uid")
    var uid: String,
    @SerializedName("description")
    var description: String

)
