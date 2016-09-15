package pl.ches.citybikes.di.module

import dagger.Module
import dagger.Provides
import pl.ches.citybikes.data.disk.store.AreaStore
import pl.ches.citybikes.data.disk.store.impl.AreaStoreImpl
import pl.ches.citybikes.di.scope.AppScope

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class DiskModule {

  @AppScope
  @Provides
  internal fun provideAreaStore(): AreaStore = AreaStoreImpl()

}