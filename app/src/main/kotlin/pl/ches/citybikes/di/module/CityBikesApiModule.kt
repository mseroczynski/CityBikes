package pl.ches.citybikes.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.ches.citybikes.BuildConfig
import pl.ches.citybikes.data.api.adapter.RxErrorHandlingCallAdapterFactory
import pl.ches.citybikes.data.api.cb.CityBikesApi
import pl.ches.citybikes.data.api.cb.CityBikesApiConsts
import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.api.cb.CityBikesApiServiceImpl
import pl.ches.citybikes.di.scope.AppScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class CityBikesApiModule {

  @AppScope
  @Provides
  internal fun provideApiService(api: CityBikesApi): CityBikesApiService {
    return CityBikesApiServiceImpl(api)
  }

  @AppScope
  @Provides
  internal fun provideCityBikesApi(@Named(CityBikesApi.FOR) retrofit: Retrofit): CityBikesApi {
    return retrofit.create(CityBikesApi::class.java)
  }

  @AppScope
  @Provides
  @Named(CityBikesApi.FOR)
  internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    val retrofitBuilder = Retrofit.Builder()

    return retrofitBuilder
        .baseUrl(CityBikesApiConsts.Host.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .client(okHttpClient)
        .validateEagerly(BuildConfig.DEBUG)
        .build()
  }

  @AppScope
  @Provides
  internal fun provideGson(): Gson {
    return GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create()
  }

}
