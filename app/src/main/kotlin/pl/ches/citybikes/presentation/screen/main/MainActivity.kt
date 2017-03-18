package pl.ches.citybikes.presentation.screen.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.state
import com.tinsuke.icekick.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_main.*
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.base.host.HostActivity
import pl.ches.citybikes.presentation.common.navigation.Fragments
import pl.ches.citybikes.presentation.common.util.UiUtils
import pl.ches.citybikes.presentation.screen.main.compass.CompassFragment
import pl.ches.citybikes.presentation.screen.main.map.MapFragment
import pl.ches.citybikes.presentation.screen.main.stations.StationChooseListener
import pl.ches.citybikes.presentation.screen.main.stations.StationsFragment

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainActivity : HostActivity<MainView, MainPresenter>(), MainView, StationChooseListener {

  private val component: MainComponent = App.component().plusMain()

  private var curFragmentId: Int by state(-1)

  //region Setup
  override val layoutRes: Int = R.layout.activity_main

  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): MainPresenter = component.presenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    unfreezeInstanceState(savedInstanceState)

    snv.initWithSaveInstanceState(savedInstanceState)
    snv.addSpaceItem(SpaceItem(getString(R.string.stations).toUpperCase(), R.drawable.ic_track_changes_18dp_white))
    snv.addSpaceItem(SpaceItem(getString(R.string.map).toUpperCase(), R.drawable.ic_pin_drop_18dp_white))

    snv.setCentreButtonIconColorFilterEnabled(false)
    snv.setCentreButtonIcon(R.drawable.ic_fab_old)

    snv.setSpaceOnClickListener(object : SpaceOnClickListener {
      override fun onItemClick(itemIndex: Int, itemName: String) {
        val fragId = if (itemIndex == 0) ID_STATIONS else ID_MAP
        showFragment(fragId)
      }

      override fun onItemReselected(itemIndex: Int, itemName: String) {
      }

      override fun onCentreButtonClick() {
        presenter.compassClicked()
      }
    })

    if (savedInstanceState == null) {
      showFragment(ID_STATIONS)
    }
   }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    freezeInstanceState(outState)
    snv.onSaveInstanceState(outState)
  }

  override fun onBackPressed() = if (curFragmentId != ID_STATIONS) {
//  override fun onBackPressed() = if (snv.currentItemIndex() != 0) {
    snv.changeCurrentItem(0)
  } else {
    super.onBackPressed()
  }
  //endregion

  private fun showFragment(fragId: Int, station: Station? = null) {
    if (fragId == curFragmentId) return

    val fragment: Fragment? = when (fragId) {
      ID_STATIONS -> StationsFragment.newInstance()
      ID_MAP -> MapFragment.newInstance()
      ID_COMPASS -> {
        CompassFragment.newInstance(station!!.latitude, station.longitude, UiUtils.getResId(station.freeBikes))
      }
      else -> null
    }
    fragment?.let {
      curFragmentId = fragId
      Fragments.Operator.at(this@MainActivity).reset(fragment, R.id.fragmentContainer)
    }

  }

  //region View
  override fun showStationCompass(station: Station) {
    snv.clearTints()
    showFragment(ID_COMPASS, station)
  }
  //endregion

  //region StationChooseListener
  override fun stationChosen(station: Station) = showStationCompass(station) // TODO klikniecie
  //endregion

  companion object {
    private const val ID_STATIONS = 0
    private const val ID_COMPASS = 1
    private const val ID_MAP = 2
  }

}
