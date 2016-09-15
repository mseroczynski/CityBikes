package pl.ches.citybikes.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import pl.ches.citybikes.BuildConfig
import pl.ches.citybikes.data.api.adapter.RxErrorHandlingCallAdapterFactory
import pl.ches.citybikes.data.api.nb.NextBikeApi
import pl.ches.citybikes.data.api.nb.NextBikeApiConsts
import pl.ches.citybikes.data.api.nb.NextBikeApiService
import pl.ches.citybikes.data.api.nb.NextBikeApiServiceImpl
import pl.ches.citybikes.di.scope.AppScope
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class NextBikeApiModule {

  @AppScope
  @Provides
  internal fun provideNextBikeApiService(api: NextBikeApi): NextBikeApiService {
    return NextBikeApiServiceImpl(api)
  }

  @AppScope
  @Provides
  internal fun provideNextBikeApi(@Named(NextBikeApi.FOR) retrofit: Retrofit): NextBikeApi {
    return retrofit.create(NextBikeApi::class.java)
  }

  @AppScope
  @Provides
  @Named(NextBikeApi.FOR)
  internal fun provideRetrofit(serializer: Serializer, okHttpClient: OkHttpClient): Retrofit {
    val retrofitBuilder = Retrofit.Builder()

    return retrofitBuilder
        .baseUrl(NextBikeApiConsts.Host.BASE_URL)
        .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .client(okHttpClient)
        .validateEagerly(BuildConfig.DEBUG)
        .build()
  }

  @AppScope
  @Provides
  internal fun provideSerializer(): Serializer {
    val strategy = AnnotationStrategy()
    return Persister(strategy)
  }

}
