package pl.ches.citybikes.presentation.screen.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.presentation.common.base.host.HostActivity
import pl.ches.citybikes.presentation.common.navigation.Fragments
import pl.ches.citybikes.presentation.screen.main.stations.StationsFragment

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainActivity : HostActivity<MainView, MainPresenter>(), MainView {

  private val component: MainComponent = App.component().plusMain()

  //region Setup
  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): MainPresenter = component.presenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    snv.initWithSaveInstanceState(savedInstanceState)
    snv.addSpaceItem(SpaceItem("Stations", R.mipmap.ic_launcher))
    snv.addSpaceItem(SpaceItem("Map", R.mipmap.ic_launcher))
    snv.setSpaceOnClickListener(object : SpaceOnClickListener {
      override fun onItemClick(itemIndex: Int, itemName: String) {
        displayFragment(itemIndex)
      }

      override fun onItemReselected(itemIndex: Int, itemName: String) {}

      override fun onCentreButtonClick() {
      }
    })

    if(savedInstanceState == null)
      displayFragment(0)
  }

  override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    super.onSaveInstanceState(outState, outPersistentState)
    snv.onSaveInstanceState(outState)
  }
  //endregion

  private fun displayFragment(itemIndex: Int) {
    val fragment: Fragment? = when (itemIndex) {
      0 -> StationsFragment.newInstance()
      else -> null
    }
    fragment?.let {
      Fragments.Operator.at(this@MainActivity).reset(it, R.id.fragmentContainer)
    }
  }

  //region View
  //endregion

}
