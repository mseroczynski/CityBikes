package pl.ches.citybikes.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.ches.citybikes.di.scope.AppScope

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class CommonModule {

  @AppScope
  @Provides
  internal fun provideReactiveLocationProvider(context: Context): ReactiveLocationProvider {
    return ReactiveLocationProvider(context)
  }

}