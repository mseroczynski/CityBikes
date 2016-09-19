package pl.ches.citybikes.presentation.screen.main.stations

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
import pl.ches.citybikes.presentation.common.widget.rv.ItemOffsetDecoration

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationsFragment : BaseLceViewStateFragmentSrl<SwipeRefreshLayout, List<Station>, StationsView, StationsPresenter>(), StationsView, StationsAdapter.Listener {

  private val component = App.component().plustStations()

  private lateinit var adapter: StationsAdapter

  //region Setup
  override val layoutRes = R.layout.fragment_stations

  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): StationsPresenter = component.presenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    appBarLay.offsetChanges().subscribe { contentView.isEnabled = it == 0 }
    contentView.refreshes().subscribe { loadData(true) }
    adapter = StationsAdapter(activity, this)
    recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter
    recyclerView.isNestedScrollingEnabled = false
    recyclerView.addItemDecoration(ItemOffsetDecoration(context, R.dimen.space_small))
  }

  override fun loadData(pullToRefresh: Boolean) = presenter.loadData(pullToRefresh)

  override fun createViewState(): LceViewState<List<Station>, StationsView> = RetainingLceViewState()

  override fun getData(): List<Station> = adapter.getItems()

  override fun setData(data: List<Station>) = adapter.setItems(data)
  //endregion

  //region Events
  override fun onStationClicked(position: Int) = presenter.stationClicked(position)
  //endregion

  companion object {
    fun newInstance() = StationsFragment()
  }

}