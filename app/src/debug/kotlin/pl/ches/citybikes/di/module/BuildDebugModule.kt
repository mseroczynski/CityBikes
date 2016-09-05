package pl.ches.citybikes.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import pl.ches.citybikes.common.DevHelper
import timber.log.Timber
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class BuildDebugModule : BuildTypedModule() {

  override fun provideBuildTypedInterceptors(): ArrayList<Interceptor> {
    val debugInterceptors = ArrayList<Interceptor>()

    // Logging interceptor
    if (DevHelper.RETROFIT_LOGS) {
      val httpLoggingInterceptor = HttpLoggingInterceptor(
          HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      debugInterceptors.add(httpLoggingInterceptor)
    }

    // Stetho interceptor
    if (DevHelper.STETHO_LOGS) {
      debugInterceptors.add(StethoInterceptor())
    }

    return debugInterceptors
  }

}