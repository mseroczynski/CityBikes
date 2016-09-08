package pl.ches.citybikes.interactor

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.interactor.base.BaseInteractor
import rx.Scheduler

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class GetAreasInteractor(jobScheduler: Scheduler, postJobScheduler: Scheduler)
: BaseInteractor<Unit?, GetAreasResult>(jobScheduler, postJobScheduler)

data class GetAreasResult(val type: GetAreasResultType, val areas: List<Area>? = null)

enum class GetAreasResultType {
  SUCCESS, ERROR
}