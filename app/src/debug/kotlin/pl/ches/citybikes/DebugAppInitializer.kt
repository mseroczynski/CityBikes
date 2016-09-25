package pl.ches.citybikes

import com.codemonkeylabs.fpslibrary.TinyDancer
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import pl.ches.citybikes.common.DevHelper

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object DebugAppInitializer {

  @JvmStatic
  fun initStetho(debugApp: DebugApp) {
    val plugins = Stetho.defaultDumperPluginsProvider(debugApp)
    val modules = Stetho.defaultInspectorModulesProvider(debugApp)
    val build = Stetho.newInitializerBuilder(debugApp).enableDumpapp(plugins).enableWebKitInspector(modules).build()
    Stetho.initialize(build)
  }

  /**
   * @see [https://github.com/square/leakcanary](https://github.com/square/leakcanary)
   */
  @JvmStatic
  fun initLeakCanary(debugApp: DebugApp) {
    if (DevHelper.LEAK_CANARY) LeakCanary.install(debugApp)
  }

  /**
   * @see [https://github.com/brianPlummer/TinyDancer](https://github.com/brianPlummer/TinyDancer)
   */
  @JvmStatic
  fun initTinyDancer(debugApp: DebugApp) {
    if (DevHelper.TINY_DANCER) {
      val tinyDancerBuilder = TinyDancer.create().startingXPosition(0).startingYPosition(0)
      tinyDancerBuilder.show(debugApp)
    }
  }

}
