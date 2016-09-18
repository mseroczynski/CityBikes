package pl.ches.citybikes.presentation.screen.main

import android.view.Menu
import android.view.MenuItem
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.presentation.common.base.host.HostActivity

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

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
      R.id.menu_refresh -> {
        presenter.refresh()
        return true
      }
    }
    return false
  }
  //endregion

  //region View
  //endregion

}
