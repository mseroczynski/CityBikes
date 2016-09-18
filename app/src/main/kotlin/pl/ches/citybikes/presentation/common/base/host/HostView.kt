package pl.ches.citybikes.presentation.common.base.host

import pl.ches.citybikes.presentation.common.base.host.enums.ToastKind
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface HostView : MvpView {

  fun showToast(toastKind: ToastKind)
  fun showToast(content: String)

}