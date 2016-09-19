package pl.ches.citybikes.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import pl.ches.citybikes.common.Consts
import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.api.nb.NextBikeApiService
import pl.ches.citybikes.data.disk.store.AreaStore
import pl.ches.citybikes.data.disk.store.StationStore
import pl.ches.citybikes.data.repo.AreaRepository
import pl.ches.citybikes.data.repo.StationRepository
import pl.ches.citybikes.data.repo.impl.AreaRepositoryImpl
import pl.ches.citybikes.data.repo.impl.StationRepositoryImpl
import pl.ches.citybikes.di.scope.AppScope
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class CommonModule {

  @AppScope
  @Provides
  internal fun provideAreaRepository(cityBikesApiService: CityBikesApiService,
                                     nextBikeApiService: NextBikeApiService,
                                     areaStore: AreaStore): AreaRepository {
    return AreaRepositoryImpl(cityBikesApiService, nextBikeApiService, areaStore)
  }

  @AppScope
  @Provides
  internal fun provideStationRepository(cityBikesApiService: CityBikesApiService,
                                        nextBikeApiService: NextBikeApiService,
                                        stationStore: StationStore): StationRepository {
    return StationRepositoryImpl(cityBikesApiService, nextBikeApiService, stationStore)
  }

  @AppScope
  @Provides
  internal fun provideReactiveLocationProvider(context: Context): ReactiveLocationProvider {
    return ReactiveLocationProvider(context)
  }

  @AppScope
  @Provides
  internal fun provideOkHttpClient(buildTypeInterceptors: ArrayList<Interceptor>): OkHttpClient {

    val builder = OkHttpClient().newBuilder()
    builder.connectTimeout(Consts.Config.OK_HTTP_API_TIMEOUT_IN_S, TimeUnit.SECONDS)
    builder.readTimeout(Consts.Config.OK_HTTP_API_TIMEOUT_IN_S, TimeUnit.SECONDS)
    builder.writeTimeout(Consts.Config.OK_HTTP_API_TIMEOUT_IN_S, TimeUnit.SECONDS)

    // Build typed interceptors
    for (interceptor in buildTypeInterceptors) {
      builder.addNetworkInterceptor(interceptor)
    }

    // Turn off redirects to make it compatible with Next Bike API
    builder.followRedirects(true)

    return builder.build()
  }

  @AppScope
  @Provides
  internal fun provideCachePrefs() {

  }

}