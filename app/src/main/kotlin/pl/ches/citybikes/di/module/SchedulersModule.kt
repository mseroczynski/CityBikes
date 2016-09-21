package pl.ches.citybikes.di.module

import pl.ches.citybikes.domain.common.SchedulersProvider
import pl.ches.citybikes.domain.common.impl.SchedulersProviderImpl
import dagger.Module
import dagger.Provides
import pl.ches.citybikes.di.qualifier.Job
import pl.ches.citybikes.di.qualifier.PostJob
import pl.ches.citybikes.di.scope.AppScope
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class SchedulersModule {

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
  internal fun provideSchedulers(@Job jobScheduler: Scheduler,
                                 @PostJob postJobScheduler: Scheduler): SchedulersProvider {
    return SchedulersProviderImpl(jobScheduler, postJobScheduler)
  }

}