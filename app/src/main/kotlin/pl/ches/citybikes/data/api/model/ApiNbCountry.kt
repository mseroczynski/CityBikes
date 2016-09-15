package pl.ches.citybikes.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Root(strict = false)
class ApiNbCountry(

    @field:Attribute(required = false, name = "name")
    var name: String = "",
    @field:Attribute(required = false, name = "lat")
    var lat: Double = 0.0,
    @field:Attribute(required = false, name = "lng")
    var lng: Double = 0.0,
    @field:Attribute(required = false, name = "zoom")
    var zoom: Int = 0,
    @field:Attribute(required = false, name = "hotline")
    var hotline: String = "",
    @field:Attribute(required = false, name = "domain")
    var domain: String = "",
    @field:Attribute(required = false, name = "country")
    var country: String = "",
    @field:Attribute(required = false, name = "country_name")
    var country_name: String = "",
    @field:ElementList(required = false, entry = "city", inline = true)
    var cities: List<ApiNbCity> = ArrayList()

)