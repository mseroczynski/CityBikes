package pl.ches.citybikes.di.module

import dagger.Module
import dagger.Provides
import pl.ches.citybikes.data.repo.AreaRepository
import pl.ches.citybikes.data.repo.StationRepository
import pl.ches.citybikes.di.scope.AppScope
import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetStationsInteractor
import pl.ches.citybikes.interactor.impl.GetAreasInteractorImpl
import pl.ches.citybikes.interactor.impl.GetStationsInteractorImpl

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class InteractorModule {

  @AppScope
  @Provides
  internal fun provideGetAreasInteractor(schedulersProvider: SchedulersProvider,
                                         areaRepository: AreaRepository): GetAreasInteractor {
    return GetAreasInteractorImpl(schedulersProvider, areaRepository)
  }

  @AppScope
  @Provides
  internal fun provideGetStationsInteractor(schedulersProvider: SchedulersProvider,
                                            stationRepository: StationRepository): GetStationsInteractor {
    return GetStationsInteractorImpl(schedulersProvider, stationRepository)
  }

}