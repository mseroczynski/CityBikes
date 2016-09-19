package pl.ches.citybikes.presentation.screen.main.stations

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_station.view.*
import pl.ches.citybikes.R
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.screen.main.stations.StationsAdapter.BaseViewHolder
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 *
 * TODO subskrypcja lokalizacji / ew. w presenterze -> sortowanie wszystkich -> odswiezenie ui
 */
class StationsAdapter(private val context: Context,
                      private val listener: Listener) : RecyclerView.Adapter<BaseViewHolder>() {

  private val stations: ArrayList<Station> = ArrayList()

  override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
    when (holder) {
      is StationViewHolder -> holder.bind(position)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = StationViewHolder(parent)

  override fun getItemCount(): Int = stations.size

  fun getItems(): List<Station> = stations

  fun setItems(items: List<Station>) {
    stations.clear()
    stations.addAll(items)
  }

  inner class StationViewHolder(parent: ViewGroup) : BaseViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)) {

    init {

    }

    fun bind(position: Int) {
      val station = stations[position]
      itemView.name.text = station.originalName
      itemView.bikes.text = station.freeBikes
      // TODO
//      itemView.distance.text = station.
      itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, iconResId(station)))
    }

    private fun iconResId(station: Station): Int {
      // TODO
      return R.mipmap.ic_launcher
    }

  }

  abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)

  interface Listener {

    fun onStationClicked(position: Int)

  }

}