package pl.ches.citybikes.presentation.screen.main.compass

import android.os.Bundle
import android.support.annotation.IntegerRes
import android.view.View
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_compass.*
import pl.ches.citybikes.App
import pl.ches.citybikes.R
import pl.ches.citybikes.domain.orientation.Orientation3d
import pl.ches.citybikes.presentation.common.base.view.BaseFragment
import pl.ches.citybikes.presentation.screen.main.compass.widget.StationCompass
import pl.ches.citybikes.presentation.util.extensions.bindArgument

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class CompassFragment : BaseFragment<CompassView, CompassPresenter>(), CompassView {

  private val lat by bindArgument<Double>(ARG_LAT)
  private val lng by bindArgument<Double>(ARG_LNG)
  private val targetResId by bindArgument<Int>(ARG_TARGET_RES_ID)

  private val component: CompassComponent = App.component().plusCompass()

  private var activeStationCompass: StationCompass? = null

  //region Setup
  override val layoutRes by lazy { R.layout.fragment_compass }

  override fun injectDependencies() = component.inject(this)

  override fun createPresenter(): CompassPresenter = component.presenter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    compass2d.setTargetDrawable(targetResId)
    compass3d.setTargetDrawable(targetResId)
    presenter.initTarget(LatLng(lat, lng))
  }
  //endregion

  //region View
  override  fun set3dMode(is3dMode: Boolean) {
    // TODO Animation
    compass3d.visibility = if(is3dMode) View.VISIBLE else View.GONE
    compass2d.visibility = if(!is3dMode) View.VISIBLE else View.GONE
    activeStationCompass = if(is3dMode) compass3d else compass2d
  }

  override fun setOrientation(orientation3d: Orientation3d) {
    activeStationCompass?.setOrientation3d(orientation3d)
  }

  override fun setAngleAndDistance(direction: Double, distance: Float) {
    activeStationCompass?.setAngleAndDistance(direction, distance)
  }
  //endregion

  companion object {

    private const val ARG_LAT = "arg_lat"
    private const val ARG_LNG = "arg_lng"
    private const val ARG_TARGET_RES_ID = "arg_target_res_id"

    fun newInstance(lat: Double, lng: Double, @IntegerRes targetResId: Int) = CompassFragment().apply {
      arguments = Bundle().apply {
        putDouble(ARG_LAT, lat)
        putDouble(ARG_LNG, lng)
        putInt(ARG_TARGET_RES_ID, targetResId)
      }
    }

  }

}
