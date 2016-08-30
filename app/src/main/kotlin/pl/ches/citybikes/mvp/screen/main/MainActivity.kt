package pl.ches.citybikes.mvp.screen.main

import pl.ches.citybikes.App
import pl.ches.citybikes.mvp.common.base.host.HostActivity

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainActivity : HostActivity<MainView, MainPresenter>(), MainView {

  private lateinit var component: MainComponent

  init {
    component = App.component().plusMain()
  }

  //region Setup
  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): MainPresenter = component.presenter()
  //endregion

  //region View
  //endregion

}
