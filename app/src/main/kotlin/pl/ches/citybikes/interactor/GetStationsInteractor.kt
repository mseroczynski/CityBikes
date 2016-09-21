package pl.ches.citybikes.interactor

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.base.BaseInteractor

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class GetStationsInteractor(schedulersProvider: SchedulersProvider)
: BaseInteractor<GetStationsParam, List<Station>>(schedulersProvider)

data class GetStationsParam(val areas: List<Area>, val forceRefresh: Boolean)