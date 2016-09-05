package pl.ches.citybikes.di.module

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import pl.ches.citybikes.di.scope.AppScope
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
open class BuildTypedModule {

  @AppScope
  @Provides
  internal open fun provideBuildTypedInterceptors(): ArrayList<Interceptor> {
    return ArrayList()
  }

}