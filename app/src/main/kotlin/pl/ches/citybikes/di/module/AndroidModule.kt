package pl.ches.citybikes.di.module

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.hardware.SensorManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import pl.ches.citybikes.di.scope.AppScope

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Module
class AndroidModule(application: Application) {

  private val context: Context

  init {
    context = application as Context
  }

  @AppScope
  @Provides
  internal fun provideContext(): Context {
    return context
  }

  @AppScope
  @Provides
  internal fun provideAudioManager(context: Context): AudioManager {
    return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
  }

  @AppScope
  @Provides
  internal fun provideActivityManager(context: Context): ActivityManager {
    return context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
  }

  @AppScope
  @Provides
  internal fun provideTelephonyManager(context: Context): TelephonyManager {
    return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
  }

  @AppScope
  @Provides
  internal fun provideConnectivityManager(context: Context): ConnectivityManager {
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  @AppScope
  @Provides
  internal fun provideWindowManager(context: Context): WindowManager {
    return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  }

  @AppScope
  @Provides
  internal fun provideNotificationManager(context: Context): NotificationManager {
    return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
  }

  @AppScope
  @Provides
  internal fun provideSharedPrefs(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

  @AppScope
  @Provides
  internal fun provideResources(context: Context): Resources {
    return context.resources
  }

  @AppScope
  @Provides
  internal fun provideSensorManger(context: Context) : SensorManager {
    return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
  }

}
