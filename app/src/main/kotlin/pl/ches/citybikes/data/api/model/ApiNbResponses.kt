package pl.ches.citybikes.data.api.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Root(strict = false, name = "markers")
class ApiNbCountriesResponse(

    @field:ElementList(required = false, entry = "country", inline = true)
    var countries: List<ApiNbCountry> = ArrayList()

)
