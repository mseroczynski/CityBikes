package pl.ches.citybikes.presentation.screen.main.stations

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import kotlinx.android.synthetic.main.item_station.view.*
import pl.ches.citybikes.R
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.presentation.common.util.UiUtils
import pl.ches.citybikes.presentation.common.widget.TextDrawable
import pl.ches.citybikes.presentation.screen.main.stations.StationAdapter.BaseViewHolder
import java.util.*

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationAdapter(private val context: Context,
                     private val listener: Listener) : RecyclerView.Adapter<BaseViewHolder>() {

  private val distancedStations: ArrayList<Pair<Station, Float>> = ArrayList()

  override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
    when (holder) {
      is StationViewHolder -> holder.bind(position)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = StationViewHolder(parent)

  override fun getItemCount(): Int = distancedStations.size

  fun swapItems(new: List<Pair<Station, Float>>) {
    val stationDiffCallback = StationDiffCallback(distancedStations, new)
    val diffResult = DiffUtil.calculateDiff(stationDiffCallback)

    distancedStations.clear()
    distancedStations.addAll(new)

    diffResult.dispatchUpdatesTo(this)
  }

  fun getItems(): List<Pair<Station, Float>> = distancedStations

  interface Listener {

    fun onStationClicked(station: Station)

  }

  inner class StationViewHolder(parent: ViewGroup) : BaseViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)) {

    private lateinit var currentStation: Station

    init {
      itemView.clicks().subscribe({
        listener.onStationClicked(currentStation)
      })
    }

    fun bind(position: Int) {
      val station = distancedStations[position].first
      val distance = distancedStations[position].second

      currentStation = station

      val drawableColor = UiUtils.getColor(context, station.freeBikes)
      val circleDrawable = TextDrawable.builder().buildRound(station.freeBikes, drawableColor)

      itemView.image.setImageDrawable(circleDrawable)
      itemView.title.text = station.originalName
      itemView.subhead.text = "${distance.toInt()} m"
    }
  }

  abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)

}