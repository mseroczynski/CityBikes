package pl.ches.citybikes

import com.facebook.stetho.Stetho

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

}
