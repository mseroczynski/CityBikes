package pl.ches.citybikes.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Root(strict = false)
class ApiNbCity(

    @field:Attribute(name = "uid")
    var uid: String = "",
    @field:Attribute(required = false, name = "lat")
    var lat: Double = 0.0,
    @field:Attribute(required = false, name = "lng")
    var lng: Double = 0.0,
    @field:Attribute(required = false, name = "zoom")
    var zoom: Int = 0,
    @field:Attribute(required = false, name = "maps_icon")
    var maps_icon: String = "",
    @field:Attribute(required = false, name = "alias")
    var alias: String = "",
    @field:Attribute(required = false, name = "break")
    var breeak: String = "",
    @field:Attribute(required = false, name = "name")
    var name: String = "",
    @field:ElementList(entry = "place", inline = true)
    var places: Collection<ApiNbPlace> = ArrayList()

)