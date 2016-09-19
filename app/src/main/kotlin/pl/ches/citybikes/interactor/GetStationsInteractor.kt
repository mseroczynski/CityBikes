package pl.ches.citybikes.interactor

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.interactor.base.BaseInteractor
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class GetStationsInteractor(jobScheduler: Scheduler, postJobScheduler: Scheduler)
: BaseInteractor<GetStationsParam, List<Station>>(jobScheduler, postJobScheduler)

data class GetStationsParam(val areas: List<Area>, val forceRefresh: Boolean)