package pl.ches.citybikes.data.api.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Root(strict = false)
class ApiNbPlace(

    @field:Attribute(required = false, name = "uid")
    var uid: String = "",
    @field:Attribute(required = false, name = "lat")
    var lat: Double = 0.0,
    @field:Attribute(required = false, name = "lng")
    var lng: Double = 0.0,
    @field:Attribute(required = false, name = "name")
    var name: String = "",
    @field:Attribute(required = false, name = "spot")
    var spot: Int = 0,
    @field:Attribute(required = false, name = "number")
    var number: Int = 0,
    @field:Attribute(required = false, name = "bikes")
    var bikes: String = "",
    @field:Attribute(required = false, name = "bike_racks")
    var bikeRacks: Int = 0,
    @field:Attribute(required = false, name = "terminal_type")
    var terminalType: String = "",
    @field:Attribute(required = false, name = "bike_numbers")
    var bikeNumbers: String = "",
    @field:Attribute(required = false, name = "maintenance")
    var maintenance: Int = 0

)
