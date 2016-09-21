package pl.ches.citybikes.presentation.screen.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
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

    snv.addSpaceItem(SpaceItem("STATIONS", R.drawable.ic_track_changes_18dp_white)) // TODO StringRes
    snv.addSpaceItem(SpaceItem("MAP", R.drawable.ic_pin_drop_18dp_white))
    snv.setSpaceOnClickListener(object : SpaceOnClickListener {
      override fun onItemClick(itemIndex: Int, itemName: String) {
        displayFragment(itemIndex)
      }

      override fun onItemReselected(itemIndex: Int, itemName: String) {
      }

      override fun onCentreButtonClick() {
      }
    })
    snv.setCentreButtonIconColorFilterEnabled(false)
    snv.setCentreButtonIcon(R.drawable.ic_fab_old)
    snv.setCentreButtonIconColor(R.color.space_transparent)

    if(savedInstanceState == null) {
      displayFragment(0)
    }
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
