package pl.ches.citybikes.presentation.screen.main.stations

import android.support.v7.util.DiffUtil
import pl.ches.citybikes.data.disk.entity.Station

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationDiffCallback(private val old: List<Pair<Station, Float>>,
                          private val new: List<Pair<Station, Float>>) : DiffUtil.Callback() {

  override fun getOldListSize(): Int = old.size

  override fun getNewListSize(): Int = new.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return old[oldItemPosition].first.id == new[newItemPosition].first.id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//    val distanceDiff = Math.abs(old[oldItemPosition].second - new[newItemPosition].second)
//    return  && distanceDiff < 10
    return old[oldItemPosition].second.toInt() == new[newItemPosition].second.toInt()
  }

}