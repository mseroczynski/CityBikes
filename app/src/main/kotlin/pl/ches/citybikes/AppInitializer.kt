package pl.ches.citybikes

import android.support.v7.app.AppCompatDelegate
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import pl.ches.citybikes.domain.common.diagnostic.CrashlyticsTree
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object AppInitializer {

  @JvmStatic
  fun initVectorDrawables() {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }

  @JvmStatic
  fun initDBFlow(app: App) {
    val configBuilder = FlowConfig.Builder(app)
    FlowManager.init(configBuilder.build())
  }

  @JvmStatic
  fun initCalligraphy() {
    val calligraphyConfig = CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(
        R.attr.fontPath).build()
    CalligraphyConfig.initDefault(calligraphyConfig)
  }

  @JvmStatic
  fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(CrashlyticsTree())
    }
  }

}
