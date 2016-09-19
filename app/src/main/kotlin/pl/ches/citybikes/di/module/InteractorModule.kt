package pl.ches.citybikes.di.module

import dagger.Module
import dagger.Provides
import pl.ches.citybikes.data.repo.AreaRepository
import pl.ches.citybikes.data.repo.StationRepository
import pl.ches.citybikes.di.qualifier.Job
import pl.ches.citybikes.di.qualifier.PostJob
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.impl.GetAreasInteractorImpl
import pl.ches.citybikes.interactor.impl.GetStationsInteractorImpl
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class InteractorModule {

  @AppScope
  @Provides
  @Job
  internal fun provideJobScheduler(): Scheduler {
    return Schedulers.io()
  }

  @AppScope
  @Provides
  @PostJob
  internal fun providePostJobScheduler(): Scheduler {
    return AndroidSchedulers.mainThread()
  }

  @AppScope
  @Provides
  internal fun provideGetAreasInteractor(@Job jobScheduler: Scheduler,
                                         @PostJob postJobScheduler: Scheduler,
                                         areaRepository: AreaRepository): GetAreasInteractor {
    return GetAreasInteractorImpl(jobScheduler, postJobScheduler, areaRepository)
  }

  @AppScope
  @Provides
  internal fun provideGetStationsInteractor(@Job jobScheduler: Scheduler,
                                            @PostJob postJobScheduler: Scheduler,
                                            stationRepository: StationRepository): GetStationsInteractor {
    return GetStationsInteractorImpl(jobScheduler, postJobScheduler, stationRepository)
  }

}