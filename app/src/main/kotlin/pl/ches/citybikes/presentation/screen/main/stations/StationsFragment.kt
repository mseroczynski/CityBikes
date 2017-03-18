package pl.ches.citybikes.presentation.screen.main.stations

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState
import com.jakewharton.rxbinding.support.design.widget.offsetChanges
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import kotlinx.android.synthetic.main.app_bar_stations.*
import kotlinx.android.synthetic.main.fragment_stations.*
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.base.view.BaseLceViewStateFragmentSrl
import pl.ches.citybikes.presentation.common.widget.DividerItemDecoration

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsFragment : BaseLceViewStateFragmentSrl<SwipeRefreshLayout, List<Pair<Station, Float>>, StationsView, StationsPresenter>(), StationsView, StationAdapter.Listener {

  private val component = App.component().plusStations()

  private lateinit var adapter: StationAdapter
  private lateinit var stationChooseListener: StationChooseListener

  //region Setup
  override val layoutRes by lazy { R.layout.fragment_stations }

  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): StationsPresenter = component.presenter()

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    try {
      stationChooseListener = context as StationChooseListener
    } catch (e: ClassCastException) {
      throw ClassCastException(activity.toString() + " must implement ${StationChooseListener::class.java.simpleName}")
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    appBarLay.offsetChanges().subscribe { contentView.isEnabled = it == 0 }
    contentView.refreshes().subscribe { loadData(true) }
    adapter = StationAdapter(activity, this)
    recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter
    recyclerView.isNestedScrollingEnabled = false
    val dividerItemDecoration = DividerItemDecoration(activity, R.drawable.bg_line_divider, false, false)
    recyclerView.addItemDecoration(dividerItemDecoration)
  }

  override fun loadData(pullToRefresh: Boolean) = presenter.loadData(pullToRefresh)

  override fun createViewState(): LceViewState<List<Pair<Station, Float>>, StationsView> = RetainingLceViewState()

  override fun getData(): List<Pair<Station, Float>> = adapter.getItems()

  override fun setData(data: List<Pair<Station, Float>>) {
    adapter.swapItems(data)
//    onStationClicked(data.first().first)
  }
  //endregion

  //region Events
  override fun onStationClicked(station: Station) = stationChooseListener.stationChosen(station)
  //endregion

  companion object {
    fun newInstance() = StationsFragment()
  }

}