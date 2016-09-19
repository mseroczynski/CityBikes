package pl.ches.citybikes.interactor

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.interactor.base.BaseInteractor
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class GetAreasInteractor(jobScheduler: Scheduler, postJobScheduler: Scheduler)
: BaseInteractor<GetAreasParam, List<Area>>(jobScheduler, postJobScheduler)

data class GetAreasParam(val sourceApi: SourceApi, val forceRefresh: Boolean)