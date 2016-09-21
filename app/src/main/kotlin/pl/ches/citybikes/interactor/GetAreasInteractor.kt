package pl.ches.citybikes.interactor

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.base.BaseInteractor
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class GetAreasInteractor(schedulersProvider: SchedulersProvider)
: BaseInteractor<GetAreasParam, List<Area>>(schedulersProvider)

data class GetAreasParam(val sourceApi: SourceApi, val forceRefresh: Boolean)